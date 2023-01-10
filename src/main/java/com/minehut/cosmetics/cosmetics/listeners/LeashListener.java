package com.minehut.cosmetics.cosmetics.listeners;

import com.minehut.cosmetics.util.CosmeticUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityUnleashEvent;

public class LeashListener implements Listener {

    @EventHandler
    public void onLeashBreak(EntityUnleashEvent event) {
        CosmeticUtil.readCosmetic(event.getEntity()).ifPresent(cosmetic -> event.setDropLeash(false));
    }
}
