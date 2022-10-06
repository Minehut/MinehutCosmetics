package com.minehut.cosmetics.events.skins;

import com.minehut.cosmetics.util.SkinUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class SkinModifyListener implements Listener {

    private static Component UNSKIN_CTA = Component.text()
            .append(Component.text("Please un-skin this item before proceeding!").color(NamedTextColor.YELLOW))
            .append(Component.space())
            .append(Component.text("[Click to Un-skin]").color(NamedTextColor.GREEN).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/unskin")))
            .build();

    @EventHandler
    public void onEnchantSkin(InventoryClickEvent event) {
        if (!isModifier(event.getInventory())) return;

        if (SkinUtil.isSkinned(event.getCurrentItem()) || SkinUtil.isSkinned(event.getCursor())) {
            event.getWhoClicked().sendMessage(UNSKIN_CTA);
            event.setCancelled(true);
        }
    }

    private boolean isModifier(Inventory inventory) {
        InventoryType type = inventory.getType();

        return type == InventoryType.ENCHANTING
                || type == InventoryType.ANVIL
                || type == InventoryType.WORKBENCH
                || type == InventoryType.STONECUTTER
                || type == InventoryType.GRINDSTONE
                || type == InventoryType.FURNACE
                || type == InventoryType.BLAST_FURNACE
                || type == InventoryType.SMOKER
                || type == InventoryType.BEACON;
    }
}
