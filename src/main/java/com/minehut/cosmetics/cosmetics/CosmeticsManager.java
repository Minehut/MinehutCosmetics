package com.minehut.cosmetics.cosmetics;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.config.Mode;
import com.minehut.cosmetics.cosmetics.bindings.Bindings;
import com.minehut.cosmetics.cosmetics.bindings.CosmeticBindings;
import com.minehut.cosmetics.cosmetics.events.CosmeticEquipEvent;
import com.minehut.cosmetics.cosmetics.properties.CosmeticSlot;
import com.minehut.cosmetics.cosmetics.properties.Equippable;
import com.minehut.cosmetics.cosmetics.properties.SlotHandler;
import com.minehut.cosmetics.cosmetics.properties.Tickable;
import com.minehut.cosmetics.model.profile.CosmeticProfileResponse;
import com.minehut.cosmetics.model.profile.SimpleResponse;
import com.minehut.cosmetics.model.request.EquipmentUpdateRequest;
import com.minehut.cosmetics.util.EnumUtil;
import com.minehut.cosmetics.util.messaging.Message;
import kong.unirest.HttpResponse;
import net.kyori.adventure.text.Component;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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
    private final Cache<UUID, CosmeticProfileResponse> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(3, TimeUnit.SECONDS)
            .build();

    /**
     * Map of active cosmetics for each user
     */
    private final Map<UUID, Map<CosmeticSlot, Cosmetic>> cosmeticsCache = new HashMap<>();
    private final CosmeticBindings bindings = new CosmeticBindings();
    private final Cosmetics cosmetics;

    public CosmeticsManager(Cosmetics cosmetics) {
        this.cosmetics = cosmetics;
        Bukkit.getScheduler().runTaskTimer(cosmetics, () -> cosmeticsCache.values()
                .forEach((userCosmetics) -> userCosmetics.values()
                        .forEach((cosmetic) -> {
                            if (!(cosmetic instanceof Tickable tickable))
                                return;
                            tickable.tick();
                        })),
                0, 1);
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
            final HttpResponse<CosmeticProfileResponse> response = cosmetics.networkApi().getProfile(uuid).join();
            final CosmeticProfileResponse profile = response.getBody();

            if (Cosmetics.mode() != Mode.LOBBY && profile != null) {
                this.cache.put(uuid, profile);
            }

            return Optional.ofNullable(profile);
        });
    }

    /**
     * Set cosmetic for the given user and updates their profile, assumes the
     * cosmetic type
     * based on the cosmetic passed, does not allow null cosmetics
     *
     * @param uuid       of the user
     * @param cosmetic   to apply
     * @param updateMeta whether to update metadata with this decision
     */
    public void setCosmetic(@NotNull final UUID uuid, @NotNull CosmeticSlot slot, @NotNull final Cosmetic cosmetic,
            boolean updateMeta) {
        // remove the existing cosmetic
        removeCosmetic(uuid, slot, false);

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

        getEquippedMap(uuid).put(slot, cosmetic);

        if (updateMeta) {
            updateEquipment(new EquipmentUpdateRequest(uuid, Map.of(slot, cosmetic)));
            Bukkit.getServer().getPluginManager().callEvent(new CosmeticEquipEvent(uuid, slot, cosmetic));
        }
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
        // remove the cosmetic
        getEquippedCosmetic(uuid, slot).ifPresent((cosmetic) -> {
            if (cosmetic instanceof Equippable equippable) {
                equippable.unequip();
            }
        });

        // remove the cosmetic from that players map
        getEquippedMap(uuid).remove(slot);

        if (updateMeta) {
            updateEquipment(new EquipmentUpdateRequest(uuid, Map.of(slot, null)));
            Bukkit.getServer().getPluginManager().callEvent(new CosmeticEquipEvent(uuid, slot, null));
        }
    }

    public void removeAllCosmetics(UUID uuid, boolean updateMeta) {
        Map<CosmeticSlot, Cosmetic> updates = new HashMap<>();
        for (CosmeticSlot slot : CosmeticSlot.values()) {
            // remove the cosmetic
            getEquippedCosmetic(uuid, slot).ifPresent((cosmetic) -> {
                if (cosmetic instanceof Equippable equippable) {
                    equippable.unequip();
                }
            });

            // remove the cosmetic from that players map
            getEquippedMap(uuid).remove(slot);
        }

        if (updateMeta) {
            updateEquipment(new EquipmentUpdateRequest(uuid, updates));
            updates.forEach((slot, _cosmetic) -> {
                Bukkit.getServer().getPluginManager().callEvent(new CosmeticEquipEvent(uuid, slot, null));
            });
        }
    }

    /**
     * Update equipment for the player and send message to player based on response
     * 
     * @param request
     */
    private void updateEquipment(EquipmentUpdateRequest request) {
        Bukkit.getScheduler().runTaskAsynchronously(cosmetics, () -> {
            final HttpResponse<SimpleResponse> res = cosmetics.networkApi()
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
        Bukkit.getScheduler().runTaskAsynchronously(cosmetics, () -> {
            final Mode mode = cosmetics.config().mode();

            // equip on main thread
            final Optional<Map<String, String>> equipped = switch (mode) {
                case LOBBY -> getProfile(uuid).join().map(CosmeticProfileResponse::getEquipped);
                case PLAYER_SERVER -> Optional.of(cosmetics.localStorage().loadProfile(uuid).join().getEquipped());
            };

            Bukkit.getScheduler().runTask(cosmetics,
                    () -> equipped.ifPresent(equipMap -> equipMap.forEach((slotName, qualifiedId) -> {
                        // grab the slot this cosmetic belongs to
                        EnumUtil.valueOfSafe(CosmeticSlot.class, slotName).ifPresent(slot -> {
                            // grab the cosmetic from its id
                            Cosmetic.fromQualifiedId(qualifiedId).ifPresent(cosmetic -> {
                                // this is here for legacy cosmetic profiles, probably want to invalidate those
                                // at some point
                                if (Mode.PLAYER_SERVER == mode && !cosmetic.category().isSaveLocal())
                                    return;
                                setCosmetic(uuid, slot, cosmetic, false);
                            });
                        });
                    })));
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
        Map<CosmeticSlot, Cosmetic> equipped = getEquippedMap(uuid);
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

    public void unequipAll(UUID uuid) {
        getEquippedMap(uuid).forEach((slot, cosmetic) -> {
            if (!(cosmetic instanceof Equippable equippable)) {
                return;
            }

            equippable.unequip();
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
            if (!(cosmetic instanceof Equippable equippable)) {
                return;
            }
            equippable.equip();
        });
    }

    public void equipAll(UUID uuid) {
        getEquippedMap(uuid).forEach((slot, cosmetic) -> {
            if (!(cosmetic instanceof Equippable equippable)) {
                return;
            }
            equippable.equip();
        });
    }

    /**
     * Get equipped cosmetics for this user, if the equipped map
     * has not yet been made for this user, automatically creates it
     *
     * @param uuid of the user to get equipped items for
     * @return map containing info on equipped cosmetics
     */
    public Map<CosmeticSlot, Cosmetic> getEquippedMap(UUID uuid) {
        return cosmeticsCache.computeIfAbsent(uuid, (k) -> new HashMap<>());
    }

    /**
     * Get the cosmetic for this user from the given slot
     *
     * @param uuid of the user to get cosmetics for
     * @param type of cosmetic to get
     * @return a possible cosmetic
     */
    public Optional<Cosmetic> getEquippedCosmetic(final UUID uuid, CosmeticSlot type) {
        return Optional.ofNullable(getEquippedMap(uuid).get(type));
    }

    public CosmeticBindings getBindings() {
        return bindings;
    }
}
