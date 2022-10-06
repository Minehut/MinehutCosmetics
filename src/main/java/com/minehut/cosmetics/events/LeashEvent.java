package com.minehut.cosmetics.events;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.util.EntityUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.persistence.PersistentDataType;

public class LeashEvent implements Listener {

    private final Cosmetics cosmetics;

    public LeashEvent(Cosmetics cosmetics) {
        this.cosmetics = cosmetics;
    }

    @EventHandler
    public void onLeashBreak(EntityUnleashEvent event) {
        if (event.getEntity().getPersistentDataContainer().has(cosmetics.keyManager().LEASHED, PersistentDataType.STRING)) {
            event.setCancelled(true);
        }
    }
}
