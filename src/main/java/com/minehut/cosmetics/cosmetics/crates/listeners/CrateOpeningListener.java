package com.minehut.cosmetics.cosmetics.crates.listeners;

import com.minehut.cosmetics.cosmetics.crates.CratesModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class CrateOpeningListener implements Listener {

    private final CratesModule crates;

    public CrateOpeningListener(CratesModule crates) {
        this.crates = crates;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!crates.isOpening(event.getPlayer().getUniqueId())) return;
        if (event.getFrom().toVector().equals(event.getTo().toVector())) return;
        event.setCancelled(true);
    }

    // cancel players from teleporting around in spectator mode while opening
    @EventHandler
    public void onSpectatorTeleport(PlayerTeleportEvent event) {
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.SPECTATE) return;
        if (!crates.isOpening(event.getPlayer().getUniqueId())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        crates.opening().remove(event.getPlayer().getUniqueId());
    }
}
