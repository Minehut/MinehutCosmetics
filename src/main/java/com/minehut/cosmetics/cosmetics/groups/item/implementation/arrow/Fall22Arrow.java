package com.minehut.cosmetics.cosmetics.groups.item.implementation.arrow;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticCollection;
import com.minehut.cosmetics.cosmetics.CosmeticPermission;
import com.minehut.cosmetics.ui.model.Model;

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

public class Fall22Arrow extends ItemCosmetic {

    public static final Component NAME = Component.text("Arrow of Autumn")
            .color(NamedTextColor.GOLD)
            .decoration(TextDecoration.ITALIC, false);

    public static final Supplier<ItemStack> ITEM = ItemBuilder.of(Material.ARROW)
            .display(NAME)
            .lore(
                    Component.empty(),
                    CosmeticCollection.FALL_22.tag(),
                    Component.empty()
            )
            .flags(ItemFlag.HIDE_ATTRIBUTES)
            .modelData(Model.Item.Arrow.FALL_22)
            .supplier();

    public Fall22Arrow() {
        super(
                Item.FALL_22_ARROW.name(),
                NAME,
                CosmeticPermission.hasPurchased(CosmeticCategory.ITEM.name(), Item.FALL_22_ARROW.name())
        );
    }

    @Override
    public ItemStack item() {
        return ITEM.get();
    }
}
