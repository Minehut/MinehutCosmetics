package com.minehut.cosmetics.events;

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
        cosmetics.cosmeticManager().handleConnect(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        cosmetics.cosmeticManager().handleDisconnect(event.getPlayer().getUniqueId());
    }
}
