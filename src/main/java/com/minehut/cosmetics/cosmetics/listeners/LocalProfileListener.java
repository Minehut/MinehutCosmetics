package com.minehut.cosmetics.cosmetics.listeners;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.config.Mode;
import com.minehut.cosmetics.model.profile.LocalCosmeticProfile;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Class for handling events that need to fire pertaining to {@link LocalCosmeticProfile}
 * these listeners are only fired when the plugin is using {@link Mode#PLAYER_SERVER}.
 */
public class LocalProfileListener implements Listener {

    public LocalProfileListener(Cosmetics cosmetics) {
    }

    /**
     * When a player joins load their local profile and equip any cosmetics we need to equip for them on login
     *
     * @param event join event
     */
    public void onJoin(PlayerJoinEvent event) {
    }

    /**
     * Write any changes to their cosmetic profile whenever they quit
     *
     * @param event quit event
     */
    public void onQuit(PlayerQuitEvent event) {
    }
}
