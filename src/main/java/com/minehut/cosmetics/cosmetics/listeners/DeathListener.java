package com.minehut.cosmetics.cosmetics.listeners;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import com.minehut.cosmetics.Cosmetics;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    private final Cosmetics cosmetics;

    public DeathListener(Cosmetics cosmetics) {
        this.cosmetics = cosmetics;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        cosmetics.manager().unEquipAll(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onRespawn(PlayerPostRespawnEvent event) {
        cosmetics.manager().equipAll(event.getPlayer().getUniqueId());
    }
}
