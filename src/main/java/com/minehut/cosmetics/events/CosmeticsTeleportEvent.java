package com.minehut.cosmetics.events;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.util.EntityUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.UUID;

public class CosmeticsTeleportEvent implements Listener {

    private final Cosmetics cosmetics;

    public CosmeticsTeleportEvent(Cosmetics cosmetics) {
        this.cosmetics = cosmetics;
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

    private void handleTeleport(UUID uuid) {
        cosmetics.cosmeticManager().unEquipAll(uuid);

        Bukkit.getScheduler().runTaskLater(cosmetics, () -> {
            cosmetics.cosmeticManager().equipAll(uuid);
        }, 1);
    }
}
