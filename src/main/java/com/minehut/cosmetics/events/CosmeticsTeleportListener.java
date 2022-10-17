package com.minehut.cosmetics.events;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.CosmeticsManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashSet;
import java.util.UUID;

public class CosmeticsTeleportListener implements Listener {

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
        if (event.getTo().toVector().equals(event.getFrom().toVector())) return;

        final UUID uuid = event.getPlayer().getUniqueId();

        if (portalFlag.contains(uuid)) {
            manager.equipAll(uuid);
            portalFlag.remove(uuid);
            return;
        }

        final Material type = event.getTo().getBlock().getType();

        if (Material.NETHER_PORTAL != type && Material.END_PORTAL != type && Material.END_GATEWAY != type) return;
        portalFlag.add(uuid);
        manager.unEquipAll(uuid);
    }

    private void handleTeleport(UUID uuid) {
        manager.unEquipAll(uuid);

        Bukkit.getScheduler().runTaskLater(cosmetics, () -> {
            manager.equipAll(uuid);
        }, 1);
    }
}