package com.minehut.cosmetics.cosmetics.collections.beta;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.Rarity;
import com.minehut.cosmetics.cosmetics.types.item.Item;
import com.minehut.cosmetics.cosmetics.types.item.ItemCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ExplorerSpyglass extends ItemCosmetic {
    public ExplorerSpyglass() {
        super(Item.EXPLORER_SPYGLASS.name());
    }

    @Override
    public Permission permission() {
        return Permission.hasPurchased(this);
    }

    @Override
    public Permission visibility() {
        return Permission.collectionIsActive(Collection.BETA);
    }

    @Override
    public Component name() {
        return Component.text("Explorer’s Spyglass")
                .color(rarity().display().color())
                .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.SPYGLASS)
                .display(name())
                .modelData(Model.Item.Spyglass.EXPLORER)
                .build();
    }

    @Override
    public @NotNull Rarity rarity() {
        return Rarity.RARE;
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.BETA;
    }
}
