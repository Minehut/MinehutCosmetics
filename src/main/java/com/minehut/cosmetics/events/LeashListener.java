package com.minehut.cosmetics.events;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.util.data.Key;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityUnleashEvent;

public class LeashListener implements Listener {

    private final Cosmetics cosmetics;

    public LeashListener(Cosmetics cosmetics) {
        this.cosmetics = cosmetics;
    }

    @EventHandler
    public void onLeashBreak(EntityUnleashEvent event) {
        if(Key.LEASHED.read(event.getEntity()).isPresent()) {
            event.setCancelled(true);
        }
    }
}
