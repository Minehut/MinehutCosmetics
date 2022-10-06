package com.minehut.cosmetics.cosmetics.groups.item.implementation.pickaxe;

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
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public class ExplorerPickaxe extends ItemCosmetic {

    private static final Component DISPLAY_NAME = Component.text("Explorer’s Pickaxe").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false);
    private static final Supplier<ItemStack> ITEM = ItemBuilder.of(Material.DIAMOND_PICKAXE)
            .display(DISPLAY_NAME)
            .modelData(Model.Item.PICKAXE.EXPLORER)
            .supplier();

    public ExplorerPickaxe() {
        super(
                Item.EXPLORER_PICKAXE.name(),
                DISPLAY_NAME,
                CosmeticPermission.hasPurchased(CosmeticCategory.ITEM.name(), Item.EXPLORER_PICKAXE.name()),
                ITEM
        );
    }

    @Override
    public ItemStack menuIcon() {
        return ItemBuilder.of(ITEM.get())
                .lore(
                        Component.empty(),
                        Component.text("Minehut Cosmetic: Beta").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
                        Component.empty()
                ).build();
    }
}
