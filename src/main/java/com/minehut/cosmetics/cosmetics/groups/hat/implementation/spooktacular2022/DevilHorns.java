package com.minehut.cosmetics.cosmetics.groups.hat.implementation;

import com.minehut.cosmetics.util.ItemBuilder;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticPermission;
import com.minehut.cosmetics.cosmetics.groups.hat.Hat;
import com.minehut.cosmetics.cosmetics.groups.hat.HatCosmetic;
import com.minehut.cosmetics.cosmetics.Model;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public class DevilHorns extends HatCosmetic {

    private static final Component NAME = Component.text("Devil Horns")
            .color(NamedTextColor.GOLD)
            .decoration(TextDecoration.ITALIC, false);
    private static final Supplier<ItemStack> ITEM = ItemBuilder.of(Material.DIAMOND_LEGGINGS)
            .display(NAME)
            .lore(
                    Component.empty(),
                    Component.text("Spooktacular 2022").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
                    Component.empty()
            )
            .modelData(Model.HAT.DEVIL_HORNS)
            .supplier();


    public DevilHorns() {
        super(
                Hat.DEVIL_HORNS.name(),
                NAME,
                CosmeticPermission.hasPurchased(CosmeticCategory.HAT.name(), Hat.DEVIL_HORNS.name()),
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
