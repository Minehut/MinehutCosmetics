package com.minehut.cosmetics.cosmetics.collections.halloween2022;


import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.companion.CompanionCosmetic;
import com.minehut.cosmetics.ui.model.Model;
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
                    Collection.GENERAL.tag(),
                    Component.empty()
            )
            .modelData(Model.Companion.GHOST)
            .supplier();

    public GhostCompanion() {
        super(
                com.minehut.cosmetics.cosmetics.types.companion.Companion.GHOST.name(),
                NAME,
                player -> ITEM.get(),
                new Vector(0, -1, 0),
                true,
                true,
                false
        );
    }

    @Override
    public Permission permission() {
        return Permission.hasPurchased(this);
    }

    @Override
    public Permission visibility() {
        return Permission.collectionIsActive(Collection.SPOOKY_22);
    }

    @Override
    public ItemStack menuIcon() {
        return ITEM.get();
    }
}