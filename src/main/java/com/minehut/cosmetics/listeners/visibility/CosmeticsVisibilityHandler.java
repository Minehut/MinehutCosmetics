package com.minehut.cosmetics.listeners.visibility;

import com.minehut.cosmetics.cosmetics.CosmeticsManager;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class CosmeticsVisibilityHandler implements Listener {

    private final CosmeticsManager manager;

    public CosmeticsVisibilityHandler(CosmeticsManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onSpectator(PlayerGameModeChangeEvent event) {
        manager.unEquipAll(event.getPlayer().getUniqueId());
        if (event.getNewGameMode() == GameMode.SPECTATOR) return;
        manager.equipAll(event.getPlayer().getUniqueId());
    }
}
