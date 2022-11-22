package com.minehut.cosmetics.cosmetics.collections.halloween2022;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.util.ItemBuilder;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.hat.HatCosmetic;
import com.minehut.cosmetics.ui.model.Model;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class DevilHorns extends HatCosmetic {
    public DevilHorns() {
        super(com.minehut.cosmetics.cosmetics.types.hat.Hat.DEVIL_HORNS.name());
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
    public Component name() {
        return Component.text("Devil Horns")
                .color(rarity().display().color())
                .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.DIAMOND_LEGGINGS)
                .display(name())
                .flags(ItemFlag.HIDE_ATTRIBUTES)
                .modelData(Model.Hat.DEVIL_HORNS)
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.SPOOKY_22;
    }
}
