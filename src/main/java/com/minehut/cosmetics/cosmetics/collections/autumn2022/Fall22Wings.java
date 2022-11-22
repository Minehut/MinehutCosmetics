package com.minehut.cosmetics.cosmetics.collections.autumn2022;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.Rarity;
import com.minehut.cosmetics.cosmetics.types.wing.WingCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class Fall22Wings extends WingCosmetic {

    public Fall22Wings() {
        super(com.minehut.cosmetics.cosmetics.types.wing.Wing.FALL_22.name());
    }

    @Override
    public Component name() {
        return Component.text("Wings of Autumn")
                .color(rarity().display().color())
                .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.SCUTE)
                .display(name())
                .modelData(Model.Wing.FALL_22)
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.FALL_22;
    }

    @Override
    public @NotNull Rarity rarity() {
        return Rarity.RARE;
    }
}