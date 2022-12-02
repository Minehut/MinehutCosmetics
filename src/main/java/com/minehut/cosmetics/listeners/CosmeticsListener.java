package com.minehut.cosmetics.listeners;

import com.minehut.cosmetics.Cosmetics;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CosmeticsListener implements Listener {
    private final Cosmetics cosmetics;

    public CosmeticsListener(Cosmetics cosmetics) {
        this.cosmetics = cosmetics;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        cosmetics.manager().handleConnect(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        cosmetics.manager().handleDisconnect(event.getPlayer().getUniqueId());
    }
}
