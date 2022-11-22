package com.minehut.cosmetics.cosmetics.collections.autumn2022;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.ui.model.Model;

import com.minehut.cosmetics.cosmetics.types.item.Item;
import com.minehut.cosmetics.cosmetics.types.item.ItemCosmetic;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class Fall2022Shield extends ItemCosmetic {

    public Fall2022Shield() {
        super(Item.FALL_22_SHIELD.name());
    }

    @Override
    public Component name() {
        return Component.text("Shield of Autumn")
                .color(rarity().display().color())
                .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.SHIELD)
                .display(name())
                .modelData(Model.Item.Shield.FALL_22)
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.FALL_22;
    }
}

