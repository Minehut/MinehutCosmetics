package com.minehut.cosmetics.cosmetics.groups.item.implementation.sword;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticPermission;
import com.minehut.cosmetics.cosmetics.Model;
import com.minehut.cosmetics.cosmetics.groups.item.Item;
import com.minehut.cosmetics.cosmetics.groups.item.ItemCosmetic;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public class ExplorerSword extends ItemCosmetic {

    private static final Component DISPLAY_NAME = Component.text("Explorer’s Blade")
            .color(NamedTextColor.GOLD)
            .decoration(TextDecoration.ITALIC, false);
    private static final Supplier<ItemStack> ITEM = ItemBuilder.of(Material.DIAMOND_SWORD)
            .display(DISPLAY_NAME)
            .lore(
                    Component.empty(),
                    Component.text("Minehut Cosmetic: Beta").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
                    Component.empty()
            )
            .flags(ItemFlag.HIDE_ATTRIBUTES)
            .modelData(Model.Item.SWORD.EXPLORER)
            .supplier();

    public ExplorerSword() {
        super(
                Item.EXPLORER_SWORD.name(),
                DISPLAY_NAME,
                CosmeticPermission.hasPurchased(CosmeticCategory.ITEM.name(), Item.EXPLORER_SWORD.name())
        );
    }

    @Override
    public ItemStack item() {
        return ITEM.get();
    }
}
