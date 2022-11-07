package com.minehut.cosmetics.cosmetics.groups.wing.implementation;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticCollection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.ui.model.Model;

import com.minehut.cosmetics.cosmetics.groups.wing.Wing;
import com.minehut.cosmetics.cosmetics.groups.wing.WingCosmetic;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public class BatWings extends WingCosmetic {

    private static final Component NAME = Component.text("Bat Wings")
            .color(NamedTextColor.GOLD)
            .decoration(TextDecoration.ITALIC, false);

    private static final Supplier<ItemStack> ITEM = ItemBuilder.of(Material.SCUTE)
            .display(NAME)
            .lore(
                    Component.empty(),
                    CosmeticCollection.SPOOKY_22.tag(),
                    Component.empty()
            )
            .modelData(Model.WING.BAT)
            .supplier();

    public BatWings() {
        super(Wing.BAT.name(),
                NAME,
                Permission.hasPurchased(CosmeticCategory.WING.name(), Wing.BAT.name()),
                Permission.deny());
    }

    @Override
    public ItemStack menuIcon() {
        return ITEM.get();
    }
}