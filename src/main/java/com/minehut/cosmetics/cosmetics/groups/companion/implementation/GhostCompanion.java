package com.minehut.cosmetics.cosmetics.groups.companion.implementation;


import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.ui.model.Model;

import com.minehut.cosmetics.cosmetics.groups.companion.Companion;
import com.minehut.cosmetics.cosmetics.groups.companion.CompanionCosmetic;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.function.Supplier;

public class GhostCompanion extends CompanionCosmetic {

    private static final Component NAME = Component.text("Ghosty-sama")
            .decoration(TextDecoration.ITALIC, false)
            .color(NamedTextColor.GOLD);

    private static final Supplier<ItemStack> ITEM = ItemBuilder.of(Material.SCUTE)
            .display(NAME)
            .lore(
                    Component.empty(),
                    Component.text("Spooktacular 2022").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
                    Component.empty()
            )
            .modelData(Model.COMPANION.GHOST)
            .supplier();

    public GhostCompanion() {
        super(
                Companion.GHOST.name(),
                NAME,
                Permission.hasPurchased(CosmeticCategory.COMPANION.name(), Companion.GHOST.name()),
                Permission.deny(),
                player -> ITEM.get(),
                new Vector(0, .25, 0),
                true,
                true,
                false
        );
    }

    @Override
    public ItemStack menuIcon() {
        return ITEM.get();
    }
}
