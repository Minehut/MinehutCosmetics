package com.minehut.cosmetics.events;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.CosmeticsManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CosmeticsTeleportListener implements Listener {

    private static final Set<Material> blacklist = Set.of(Material.END_PORTAL, Material.NETHER_PORTAL, Material.END_GATEWAY);

    private final Cosmetics cosmetics;
    private final CosmeticsManager manager;

    public CosmeticsTeleportListener(Cosmetics cosmetics) {
        this.cosmetics = cosmetics;
        this.manager = cosmetics.cosmeticManager();
    }

    // handle player teleportation
    @EventHandler
    public void onPortalEvent(PlayerPortalEvent event) {
        handleTeleport(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        handleTeleport(event.getPlayer().getUniqueId());
    }

    private final HashSet<UUID> portalFlag = new HashSet<>();

    @EventHandler
    public void onEnterPortal(PlayerMoveEvent event) {
        if (event.getFrom().toVector().equals(event.getTo().toVector())) return;

        final UUID uuid = event.getPlayer().getUniqueId();
        final Material targetType = event.getTo().getBlock().getType();
        final boolean shouldRemove = blacklist.contains(targetType);

        if (!shouldRemove && portalFlag.contains(uuid)) {
            cosmetics.cosmeticManager().equipAll(uuid);
            portalFlag.remove(uuid);
            return;
        }

        if (shouldRemove) {
            cosmetics.cosmeticManager().unEquipAll(uuid);
            portalFlag.add(uuid);
            return;
        }
    }

    private void handleTeleport(UUID uuid) {
        manager.unEquipAll(uuid);

        Bukkit.getScheduler().runTaskLater(cosmetics, () -> {
            manager.equipAll(uuid);
        }, 1);
    }
}