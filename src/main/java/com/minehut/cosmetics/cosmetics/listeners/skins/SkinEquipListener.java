package com.minehut.cosmetics.cosmetics.listeners.skins;

import com.minehut.cosmetics.util.BlockUtil;
import com.minehut.cosmetics.util.CosmeticUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class SkinEquipListener implements Listener {

    private static final Set<Material> BLACKLIST = Set.of(

    );

    /*
    Handle player shift clicking items into armor slots
     */
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void equipKeyedItem(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (InventoryType.CRAFTING != event.getInventory().getType()) return;

        // handle shift clicking
        final ItemStack clicked = event.getCurrentItem();
        final EntityEquipment equipment = player.getEquipment();
        final EquipmentSlot clickedSlot = CosmeticUtil.getEquipmentSlot(clicked).orElse(null);

        if (clicked != null && clickedSlot != null && event.isShiftClick()) {
            if (Material.AIR == player.getEquipment().getItem(clickedSlot).getType()) {
                player.getInventory().remove(clicked);
                equipment.setItem(clickedSlot, clicked);
                return;
            }


            if (InventoryType.SlotType.ARMOR == event.getSlotType()) {
                return;
            }

            event.setCancelled(true);
            return;
        }

        // handle clicking the head slot
        final ItemStack cursor = event.getCursor();
        final EquipmentSlot cursorSlot = CosmeticUtil.getEquipmentSlot(cursor).orElse(null);

        // they have a cursor item, and they clicked an armor slot
        if (cursor != null && cursorSlot != null && InventoryType.SlotType.ARMOR == event.getSlotType()) {
            event.setCancelled(true);

            // if we clicked the wrong item slot don't do anything
            if (event.getRawSlot() != CosmeticUtil.getEquipmentSlotIndex(cursorSlot)) {
                return;
            }

            player.setItemOnCursor(equipment.getItem(cursorSlot));
            player.getEquipment().setItem(cursorSlot, cursor);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handleInteract(PlayerInteractEvent event) {
        final ItemStack item = event.getItem();
        if (item == null) return;

        final Block block = event.getClickedBlock();
        if (block != null && BlockUtil.canInteract(block.getType())) {
            return;
        }


        final Player player = event.getPlayer();

        // if the item has a slot we want to handle it w/ custom logic
        CosmeticUtil.getEquipmentSlot(item).ifPresent(slot -> {
            event.setUseItemInHand(Event.Result.DENY);

            // if they don't have an item in the head slot, perform the swap
            if (Material.AIR == player.getEquipment().getItem(slot).getType()) {
                player.getEquipment().setItem(slot, item);

                // remove it from their hotbar if they used their main hand
                if (EquipmentSlot.HAND == event.getHand()) {
                    player.getInventory().setItem(player.getInventory().getHeldItemSlot(), null);
                }
                // otherwise remove it from their offhand
                else {
                    player.getInventory().setItemInOffHand(null);
                }

                player.updateInventory();
            }
        });
    }
}
