package com.minehut.cosmetics.cosmetics;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.config.Mode;
import com.minehut.cosmetics.cosmetics.bindings.Bindings;
import com.minehut.cosmetics.cosmetics.bindings.CosmeticBindings;
import com.minehut.cosmetics.cosmetics.properties.Equippable;
import com.minehut.cosmetics.cosmetics.properties.Tickable;
import com.minehut.cosmetics.model.profile.CosmeticProfileResponse;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CosmeticsManager {

    /**
     * Cache for retrieving player cosmetic profiles
     */
    private final Cache<UUID, CosmeticProfileResponse> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();

    /**
     * Map of active cosmetics for each user
     */
    private final Map<UUID, Map<CosmeticCategory, Cosmetic>> cosmeticsCache = new HashMap<>();
    private final CosmeticBindings bindings = new CosmeticBindings();
    private final Cosmetics cosmetics;

    public CosmeticsManager(Cosmetics cosmetics) {
        this.cosmetics = cosmetics;
        Bukkit.getScheduler().runTaskTimer(cosmetics, () -> cosmeticsCache.values()
                .forEach((userCosmetics) -> userCosmetics.values()
                        .forEach((cosmetic) -> {
                            if (!(cosmetic instanceof Tickable tickable)) return;
                            tickable.tick();
                        })
                ), 0, 1);
        bindings.registerBinding(Bindings.ALL);
    }

    /**
     * Request the given users cosmetic profile
     *
     * @param uuid of the user
     * @return a future that contains the users cosmetic profile
     */
    public CompletableFuture<Optional<CosmeticProfileResponse>> getProfile(UUID uuid) {

        var cached = cache.getIfPresent(uuid);
        if (cached != null) {
            return CompletableFuture.completedFuture(Optional.of(cached));
        }

        return CompletableFuture.supplyAsync(() -> {
            final Optional<CosmeticProfileResponse> response = cosmetics.api().getProfile(uuid);
            response.ifPresent(res -> this.cache.put(uuid, res));
            return response;
        });
    }

    /**
     * Set cosmetic for the given user and updates their profile, assumes the cosmetic type
     * based on the cosmetic passed, does not allow null cosmetics
     *
     * @param uuid       of the user
     * @param cosmetic   to apply
     * @param updateMeta whether to update metadata with this decision
     */
    public void setCosmetic(@NotNull final UUID uuid, @NotNull final Cosmetic cosmetic, boolean updateMeta) {
        // remove the existing cosmetic
        removeCosmetic(uuid, cosmetic.category(), false);

        // equip the item
        cosmetic.owner(uuid);
        if (cosmetic instanceof Equippable equippable) {
            equippable.equip();
        }
        getEquippedForUser(uuid).put(cosmetic.category(), cosmetic);

        if (updateMeta) {
            sendEquipmentUpdate(uuid, cosmetic.category(), cosmetic.id());
        }
    }

    /**
     * Remove equipment for the given uuid and cosmetic type
     *
     * @param uuid       of the player to remove cosmetics for
     * @param category   of cosmetic to remove
     * @param updateMeta whether to update the players meta for this removal, defaults to true
     */
    public void removeCosmetic(UUID uuid, CosmeticCategory category, boolean updateMeta) {
        // remove the cosmetic
        getCosmeticForUser(uuid, category).ifPresent((cosmetic) -> {
            if (cosmetic instanceof Equippable equippable) {
                equippable.unequip();
            }
        });
        // remove the cosmetic from that players map
        getEquippedForUser(uuid).remove(category);

        if (updateMeta) {
            sendEquipmentUpdate(uuid, category, "EMPTY");
        }
    }


    /**
     * Updates this users equipment meta
     *
     * @param uuid     of the user to update data for
     * @param category of equipment to update
     * @param id       id for the cosmetic to equip
     */
    private void sendEquipmentUpdate(UUID uuid, CosmeticCategory category, String id) {
        Bukkit.getScheduler().runTaskAsynchronously(cosmetics, () -> cosmetics.api().equipCosmetic(uuid, category.name(), id));
    }

    /**
     * Handle a player connecting to the server, this is used to apply
     * cosmetics the player had saved whenever they log in
     *
     * @param uuid of the player connecting
     */
    public void handleConnect(final UUID uuid) {
        Bukkit.getScheduler().runTaskAsynchronously(cosmetics, () -> {
            Optional<Map<String, String>> equipped = Optional.empty();

            switch (cosmetics.config().mode()) {
                case LOBBY -> equipped = getProfile(uuid).join().map(CosmeticProfileResponse::getEquipped);
                case PLAYER_SERVER ->
                        equipped = Optional.of(cosmetics.localStorage().loadProfile(uuid).join().getEquipped());
            }

            // equip on main thread
            final Optional<Map<String, String>> finalEquipped = equipped;
            Bukkit.getScheduler().runTask(cosmetics, () -> equipFromId(uuid, finalEquipped.orElseGet(HashMap::new)));
        });
    }

    /**
     * Handles the disconnection of a user with cosmetics, ensures that
     * all cosmetics the user has equipped are unequipped, and
     * any cosmetics that use ticks are no longer processed.
     *
     * @param uuid of the user to handle disconnection for
     */
    public void handleDisconnect(final UUID uuid) {
        cache.invalidate(uuid);

        // un-equip and clear the players map
        Map<CosmeticCategory, Cosmetic> equipped = getEquippedForUser(uuid);
        equipped.values().forEach((cosmetic) -> {
            // un-equip if that's possible
            if (cosmetic instanceof Equippable equippable) {
                equippable.unequip();
            }
        });

        // if we're in player server mode we want to write their equipped cosmetics
        // to a file before clearing it
        if (Mode.PLAYER_SERVER == cosmetics.config().mode()) {
            cosmetics.localStorage().writeProfile(uuid, new HashMap<>(equipped));
        }

        // clear the equipped map for this player, this stops any ticking entities
        equipped.clear();
    }

    public HashSet<Cosmetic> getEquipped(UUID uuid) {
        return Optional.ofNullable(cosmeticsCache.get(uuid))
                .map(Map::values)
                .map(HashSet::new)
                .orElseGet(HashSet::new);
    }

    public void unEquipAll(UUID uuid) {
        getEquipped(uuid).forEach(cosmetic -> {
            if (!(cosmetic instanceof Equippable equippable)) return;
            equippable.unequip();
        });
    }

    public void equipAll(UUID uuid) {
        getEquipped(uuid).forEach(cosmetic -> {
            if (!(cosmetic instanceof Equippable equippable)) return;
            equippable.equip();
        });
    }

    private void equipFromId(UUID uuid, Map<String, String> equipped) {

        final HashMap<CosmeticCategory, Cosmetic> cosmetics = new HashMap<>();

        // grab cosmetics using the ids retrieved from the profile
        equipped.forEach((category, id) ->
                CosmeticCategory.getCosmetic(category, id)
                        .ifPresent(cosmetic -> cosmetics.put(cosmetic.category(), cosmetic))
        );

        // Equip cosmetics on the main bukkit thread
        cosmetics.forEach((category, cosmetic) -> this.cosmetics.cosmeticManager().setCosmetic(uuid, cosmetic, false));
    }

    /**
     * Get equipped cosmetics for this user, if the equipped map
     * has not yet been made for this user, automatically creates it
     *
     * @param uuid of the user to get equipped items for
     * @return map containing info on equipped cosmetics
     */
    public Map<CosmeticCategory, Cosmetic> getEquippedForUser(UUID uuid) {
        return cosmeticsCache.computeIfAbsent(uuid, (k) -> new HashMap<>());
    }

    /**
     * Get the cosmetic for this user from the given category
     *
     * @param uuid of the user to get cosmetics for
     * @param type of cosmetic to get
     * @return a possible cosmetic
     */
    public Optional<Cosmetic> getCosmeticForUser(final UUID uuid, CosmeticCategory type) {
        return Optional.ofNullable(getEquippedForUser(uuid).get(type));
    }

    public CosmeticBindings getBindings() {
        return bindings;
    }
}
