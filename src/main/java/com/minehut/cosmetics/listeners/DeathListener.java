package com.minehut.cosmetics.events;

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
        cosmetics.cosmeticManager().unEquipAll(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onRespawn(PlayerPostRespawnEvent event) {
        cosmetics.cosmeticManager().equipAll(event.getPlayer().getUniqueId());
    }
}
