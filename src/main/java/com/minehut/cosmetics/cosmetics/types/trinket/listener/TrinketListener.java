package com.minehut.cosmetics.cosmetics.types.trinket.listener;

import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.util.SkinUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantInventory;

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
        if (!containsIllegalItems(event.getInventory())) return;
        event.setCancelled(true);
    }

    private static final int TRADE_COMPLETE_SLOT = 2;

    @EventHandler
    public void onTrade(InventoryClickEvent event) {

        if (!(event.getInventory() instanceof MerchantInventory inventory)) return;

        if (InventoryType.MERCHANT != inventory.getType() || TRADE_COMPLETE_SLOT != event.getRawSlot()) return;
        if (inventory.getItem(event.getRawSlot()) == null) return;
        if (!containsIllegalItems(inventory)) return;

        event.setCancelled(true);
    }

    /**
     * Checks if the given inventory contains any "illegal" items.
     * illegal items are classified as items that are marked as "cosmetic"
     *
     * @param inventory to scan for illegal items
     */
    private boolean containsIllegalItems(Inventory inventory) {
        for (final ItemStack item : inventory) {
            final Optional<Cosmetic> cosmetic = SkinUtil.getCosmetic(item);
            if (cosmetic.isEmpty()) continue;
            return true;
        }
        return false;
    }
}
