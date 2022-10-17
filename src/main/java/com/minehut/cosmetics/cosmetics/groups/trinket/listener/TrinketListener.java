package com.minehut.cosmetics.cosmetics.groups.trinket.listener;

import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.util.SkinUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class TrinketListener implements Listener {

    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        SkinUtil.getCosmetic(event.getItem()).ifPresent(cosmetic -> {
            if (CosmeticCategory.TRINKET != cosmetic.category()) return;
            event.setCancelled(true);
        });
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {

        for (final ItemStack item : event.getInventory()) {
            final Optional<Cosmetic> cosmetic = SkinUtil.getCosmetic(item);
            if (cosmetic.isEmpty()) return;
            event.setCancelled(true);
        }
    }
}
