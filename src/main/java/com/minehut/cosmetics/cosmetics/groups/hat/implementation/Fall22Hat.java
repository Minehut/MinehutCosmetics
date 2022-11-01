package com.minehut.cosmetics.cosmetics.groups.hat.implementation;

import com.minehut.cosmetics.cosmetics.CosmeticCollection;
import com.minehut.cosmetics.util.ItemBuilder;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticPermission;
import com.minehut.cosmetics.cosmetics.groups.hat.Hat;
import com.minehut.cosmetics.cosmetics.groups.hat.HatCosmetic;
import com.minehut.cosmetics.ui.model.Model;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public class Fall22Hat extends HatCosmetic {

    private static final Component NAME = Component.text("Helmet of Autumn")
            .color(NamedTextColor.GOLD)
            .decoration(TextDecoration.ITALIC, false);
    private static final Supplier<ItemStack> ITEM = ItemBuilder.of(Material.DIAMOND_LEGGINGS)
    .display(NAME)
    .lore(
            Component.empty(),
            CosmeticCollection.FALL_22.tag(),
            Component.empty()
    )
    .flags(ItemFlag.HIDE_ATTRIBUTES)
    .modelData(Model.HAT.FALL_22)
    .supplier();


    public Fall22Hat() {
        super(
                Hat.FALL_22.name(),
                NAME,
                CosmeticPermission.hasPurchased(CosmeticCategory.HAT.name(), Hat.FALL_22.name()),
                ITEM
        );
    }

    @Override
    public Component name() {
        return NAME;
    }

    @Override
    public ItemStack menuIcon() {
        return ITEM.get();
    }
}
