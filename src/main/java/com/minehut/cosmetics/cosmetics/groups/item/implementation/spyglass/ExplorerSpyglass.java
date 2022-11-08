package com.minehut.cosmetics.cosmetics.groups.item.implementation.spyglass;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticCollection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.groups.item.Item;
import com.minehut.cosmetics.cosmetics.groups.item.ItemCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public class ExplorerSpyglass extends ItemCosmetic {

    private static final Component DISPLAY_NAME = Component.text("Explorer’s Spyglass")
            .color(NamedTextColor.GOLD)
            .decoration(TextDecoration.ITALIC, false);
    private static final Supplier<ItemStack> ITEM = ItemBuilder.of(Material.SPYGLASS)
            .display(DISPLAY_NAME)
            .lore(
                    Component.empty(),
                    CosmeticCollection.BETA.tag(),
                    Component.empty()
            )
            .modelData(Model.Item.Spyglass.EXPLORER)
            .supplier();

    public ExplorerSpyglass() {
        super(Item.EXPLORER_SPYGLASS.name(), DISPLAY_NAME);
    }

    @Override
    public ItemStack item() {
        return ITEM.get();
    }

    @Override
    public Permission permission() {
        return Permission.hasPurchased(CosmeticCategory.ITEM.name(), Item.EXPLORER_SPYGLASS.name());
    }

    @Override
    public Permission visibility() {
        return Permission.collectionIsActive(CosmeticCollection.BETA);
    }
}
