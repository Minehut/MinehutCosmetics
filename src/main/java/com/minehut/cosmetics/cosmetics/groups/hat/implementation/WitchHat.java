package com.minehut.cosmetics.cosmetics.groups.hat.implementation;

import com.minehut.cosmetics.util.ItemBuilder;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.Permission;
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

public class WitchHat extends HatCosmetic {

    private static final Component NAME = Component.text("Witches Hat")
            .color(NamedTextColor.GOLD)
            .decoration(TextDecoration.ITALIC, false);
    private static final Supplier<ItemStack> ITEM = ItemBuilder.of(Material.DIAMOND_LEGGINGS)
            .display(NAME)
            .lore(
                    Component.empty(),
                    Component.text("Spooktacular 2022").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
                    Component.empty()
            )
            .flags(ItemFlag.HIDE_ATTRIBUTES)
            .modelData(Model.HAT.WITCH)
            .supplier();


    public WitchHat() {
        super(
                Hat.WITCH.name(),
                NAME,
                Permission.hasPurchased(CosmeticCategory.HAT.name(), Hat.WITCH.name()),
                Permission.deny(),
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
