package com.minehut.cosmetics.cosmetics.groups.item.implementation.spyglass;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.util.ItemBuilder;
import com.minehut.cosmetics.cosmetics.CosmeticPermission;
import com.minehut.cosmetics.cosmetics.groups.item.Item;
import com.minehut.cosmetics.cosmetics.groups.item.ItemCosmetic;
import com.minehut.cosmetics.cosmetics.Model;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ExplorerSpyglass extends ItemCosmetic {

    private static final Component DISPLAY_NAME = Component.text("Explorer’s Spyglass").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false);
    private static final ItemStack ITEM = ItemBuilder.of(Material.SPYGLASS)
            .display(DISPLAY_NAME)
            .modelData(Model.Item.SPYGLASS.EXPLORER)
            .build();

    public ExplorerSpyglass() {
        super(
                Item.EXPLORER_SPYGLASS.name(),
                DISPLAY_NAME,
                CosmeticPermission.hasPurchased(CosmeticCategory.ITEM.name(), Item.EXPLORER_SPYGLASS.name()),
                ITEM::clone
        );
    }

    @Override
    public ItemStack item() {
        return ItemBuilder.of(ITEM.clone())
                .lore(Component.empty(),
                        Component.text("Minehut Cosmetic: Beta").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
                        Component.empty()
                ).build();
    }
}
