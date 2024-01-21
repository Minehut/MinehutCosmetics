package com.minehut.cosmetics.cosmetics;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.config.Mode;
import com.minehut.cosmetics.cosmetics.bindings.Bindings;
import com.minehut.cosmetics.cosmetics.bindings.CosmeticBindings;
import com.minehut.cosmetics.cosmetics.events.CosmeticUpdateEvent;
import com.minehut.cosmetics.cosmetics.properties.CosmeticSlot;
import com.minehut.cosmetics.cosmetics.properties.Equippable;
import com.minehut.cosmetics.cosmetics.properties.SlotHandler;
import com.minehut.cosmetics.cosmetics.properties.Tickable;
import com.minehut.cosmetics.model.profile.CosmeticProfileResponse;
import com.minehut.cosmetics.model.profile.SimpleResponse;
import com.minehut.cosmetics.model.request.CosmeticState;
import com.minehut.cosmetics.model.request.EquipmentUpdateRequest;
import com.minehut.cosmetics.util.EnumUtil;
import com.minehut.cosmetics.util.messaging.Message;
import kong.unirest.HttpResponse;
import net.kyori.adventure.text.Component;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CosmeticsManager {

    /**
     * Cache for retrieving player cosmetic profiles
     */
    private final Cache<UUID, CosmeticProfileResponse> responseCache = CacheBuilder.newBuilder()
            .expireAfterWrite(3, TimeUnit.SECONDS)
            .build();

    /**
     * Map of active cosmetics for each user
     */
    private final Map<UUID, CosmeticState> cache = new HashMap<>();
    private final CosmeticBindings bindings = new CosmeticBindings();

    public CosmeticsManager() {
        Bukkit.getScheduler().runTaskTimer(Cosmetics.get(),
                () -> cache.values().forEach(cached -> cached.forEach((slot, cosmetic) -> {
                    if (cosmetic instanceof Tickable tickable) {
                        tickable.tick();
                    }
                })), 0, 1);
        bindings.registerBinding(Bindings.ALL);
    }

    /**
     * Request the given users cosmetic profile
     *
     * @param uuid of the user
     * @return a future that contains the users cosmetic profile
     */
    public CompletableFuture<Optional<CosmeticProfileResponse>> getProfile(UUID uuid) {

        var cached = responseCache.getIfPresent(uuid);
        if (cached != null) {
            return CompletableFuture.completedFuture(Optional.of(cached));
        }

        return CompletableFuture.supplyAsync(() -> {
            final HttpResponse<CosmeticProfileResponse> response = Cosmetics.get().networkApi().getProfile(uuid).join();
            final CosmeticProfileResponse profile = response.getBody();

            if (Cosmetics.mode() != Mode.LOBBY && profile != null) {
                this.responseCache.put(uuid, profile);
            }

            return Optional.ofNullable(profile);
        });
    }

    /**
     * Bind the cosmetic to the given user and update
     * their cosmetic state
     * 
     * @param uuid     of the player
     * @param slot     to put the cosmetic in
     * @param cosmetic to put in that slot
     */
    public void applyCosmetic(@NotNull final UUID uuid, @NotNull CosmeticSlot slot, Cosmetic cosmetic) {
        if (cosmetic != null) {
            // equip the item
            cosmetic.owner(uuid);

            // if we need to handle slots, set it immediately
            if (cosmetic instanceof SlotHandler slotHandler) {
                slotHandler.setSlot(slot);
            }

            // if the item is equippable, equip it.
            if (cosmetic instanceof Equippable equippable) {
                equippable.equip();
            }
        }

        getState(uuid).set(slot, cosmetic);
        new CosmeticUpdateEvent(uuid, slot, cosmetic).callEvent();
    }

    /**
     * Remove equipment for the given uuid and cosmetic type
     *
     * @param uuid       of the player to remove cosmetics for
     * @param slot       of the cosmetic to remove
     * @param updateMeta whether to update the players meta for this removal,
     *                   defaults to true
     */
    public void removeCosmetic(UUID uuid, CosmeticSlot slot, boolean updateMeta) {
        applyCosmetic(uuid, slot, null);
        new CosmeticUpdateEvent(uuid, slot, null).callEvent();

        if (updateMeta) {
            updateEquipment(uuid);
        }
    }

    public void removeAllCosmetics(UUID uuid, boolean updateMeta) {
        for (CosmeticSlot slot : CosmeticSlot.values()) {
            removeCosmetic(uuid, slot, false);
        }

        if (updateMeta) {
            updateEquipment(uuid);
        }
    }

    /**
     * Update equipment for the player and send message to player based on response
     * 
     * @param request
     */
    public void updateEquipment(UUID uuid) {
        EquipmentUpdateRequest request = new EquipmentUpdateRequest(uuid, getState(uuid));
        Bukkit.getScheduler().runTaskAsynchronously(Cosmetics.get(), () -> {
            final HttpResponse<SimpleResponse> res = Cosmetics.get().networkApi()
                    .updateEquipment(request)
                    .join();
            if (res == null) {
                return;
            }

            final Player player = Bukkit.getPlayer(request.getUuid());
            if (player != null) {
                Component message = switch (res.getStatus()) {
                    case 200 -> Message.info("Updated equipment.");
                    case 429 -> Message.error("Please wait a moment and try again...");
                    default -> Message.error("Failed to update equipment");
                };

                player.sendActionBar(message);
            }

        });
    }

    /**
     * Handle a player connecting to the server, this is used to apply
     * cosmetics the player had saved whenever they log in
     *
     * @param uuid of the player connecting
     */
    public void handleConnect(final UUID uuid) {
        Bukkit.getScheduler().runTaskAsynchronously(Cosmetics.get(), () -> {

            // equip on main thread
            final Optional<CosmeticState> maybeState = switch (Cosmetics.mode()) {
                case LOBBY -> getProfile(uuid).join().map(CosmeticProfileResponse::getEquipped);
                case PLAYER_SERVER ->
                    Optional.of(Cosmetics.get().localStorage().loadProfile(uuid).join().getEquipped());
            };

            CosmeticState state = maybeState.orElse(null);

            if (state != null) {
                Bukkit.getScheduler().runTask(Cosmetics.get(), () -> {
                    for (CosmeticSlot slot : CosmeticSlot.values()) {
                        Cosmetic cosmetic = state.get(slot);
                        if (cosmetic == null
                                || (Cosmetics.mode() == Mode.PLAYER_SERVER && !cosmetic.category().isSaveLocal())) {
                            continue;
                        }

                        applyCosmetic(uuid, slot, cosmetic);
                    }
                });
            }
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
        CosmeticState state = getState(uuid);
        removeAllCosmetics(uuid, false);

        // if we're in player server mode we want to write their equipped cosmetics
        // to a file before clearing it
        if (Mode.PLAYER_SERVER == Cosmetics.mode()) {
            Cosmetics.get().localStorage().writeProfile(uuid, state);
        }

        responseCache.invalidate(uuid);
    }

    public void unequipAll(UUID uuid) {
        getState(uuid).forEach((slot, cosmetic) -> {
            if (cosmetic instanceof Equippable equippable) {
                equippable.unequip();
            }
        });
    }

    /**
     * Run the equip method on the cosmetic in the
     * provided slot
     *
     * @param uuid user to equip for
     * @param slot to equip in
     */
    public void equip(UUID uuid, CosmeticSlot slot) {
        getEquippedCosmetic(uuid, slot).ifPresent(cosmetic -> {
            if (cosmetic instanceof Equippable equippable) {
                equippable.equip();
            }
        });
    }

    public void equipAll(UUID uuid) {
        getState(uuid).forEach((slot, cosmetic) -> {
            if (cosmetic instanceof Equippable equippable) {
                equippable.equip();
            }
        });
    }

    /**
     * Get equipped cosmetics for this user, if the equipped map
     * has not yet been made for this user, automatically creates it
     *
     * @param uuid of the user to get equipped items for
     * @return map containing info on equipped cosmetics
     */
    public CosmeticState getState(UUID uuid) {
        return cache.computeIfAbsent(uuid, ignored -> new CosmeticState());
    }

    /**
     * Get the cosmetic for this user from the given slot
     *
     * @param uuid of the user to get cosmetics for
     * @param type of cosmetic to get
     * @return a possible cosmetic
     */
    public Optional<Cosmetic> getEquippedCosmetic(final UUID uuid, CosmeticSlot type) {
        return Optional.ofNullable(getState(uuid).get(type));
    }

    public CosmeticBindings getBindings() {
        return bindings;
    }
}
