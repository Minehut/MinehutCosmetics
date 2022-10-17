package com.minehut.cosmetics.cosmetics.ui;

import com.minehut.cosmetics.util.ItemBuilder;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.groups.item.Item;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Supplier;

public class ItemMenu extends CosmeticSubMenu {

    public static final Supplier<ItemStack> ICON = ItemBuilder.of(Material.GOLDEN_SWORD)
            .flags(ItemFlag.HIDE_ATTRIBUTES)
            .display(Component.text("Equipment").color(NamedTextColor.AQUA))
            .lore(
                    Component.empty(),
                    Component.text("Items to carry around!").color(NamedTextColor.WHITE),
                    Component.empty()
            ).supplier();


    public ItemMenu() {
        super(CosmeticCategory.ITEM, List.of(
                // Explorer
                Item.EXPLORER_SWORD,
                Item.EXPLORER_PICKAXE,
                Item.EXPLORER_SPYGLASS,
                // Fall 2022
                Item.FALL_22_SWORD,
                Item.FALL_22_SHIELD,
                Item.FALL_22_PICKAXE,
                Item.FALL_22_AXE,
                Item.FALL_22_SHOVEL,
                // Spooktacular 2022
                Item.GRAVE_SHOVEL
        ));
    }
    
    @Override
    public ItemStack icon() {
        return ICON.get();
    }
}
