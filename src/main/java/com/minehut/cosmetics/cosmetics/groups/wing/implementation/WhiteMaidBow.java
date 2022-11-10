package com.minehut.cosmetics.cosmetics.groups.wing.implementation;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.Collection;
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

public class WhiteMaidBow extends WingCosmetic {

    private static final Component NAME = Component.text("Maid's White Bow")
            .color(NamedTextColor.AQUA)
            .decoration(TextDecoration.ITALIC, false);

    private static final Supplier<ItemStack> ITEM = ItemBuilder.of(Material.SCUTE)
            .display(NAME)
            .lore(
                    Component.empty(),
                    Collection.MAID.tag(),
                    Component.empty()
            )
            .modelData(Model.WING.BOW_WHITE)
            .supplier();

    public WhiteMaidBow() {
        super(Wing.BOW_WHITE.name(), NAME);
    }

    @Override
    public Permission permission() {
        return Permission.hasPurchased(this);
    }

    @Override
    public Permission visibility() {
        return Permission.deny();
    }

    @Override
    public ItemStack menuIcon() {
        return ITEM.get();
    }
}