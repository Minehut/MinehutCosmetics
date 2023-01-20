package com.minehut.cosmetics.cosmetics.collections.jan2023;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.hat.Hat;
import com.minehut.cosmetics.cosmetics.types.hat.HatCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class NinjaMask extends HatCosmetic {

    public NinjaMask() {
        super(Hat.NINJA_MASK.name());
    }

    @Override
    public Component name() {
        return Component.text("Ninja Mask")
            .color(rarity().display().color())
            .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.DIAMOND_LEGGINGS)
            .display(name())
            .flags(ItemFlag.HIDE_ATTRIBUTES)
            .modelData(Model.Hat.NINJA_MASK)
            .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.NINJA;
    }
}
