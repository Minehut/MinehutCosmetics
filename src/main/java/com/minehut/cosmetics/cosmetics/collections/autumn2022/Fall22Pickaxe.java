package com.minehut.cosmetics.cosmetics.collections.autumn2022;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.types.item.Item;
import com.minehut.cosmetics.cosmetics.types.item.ItemCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Fall22Pickaxe extends ItemCosmetic {

    public Fall22Pickaxe() {
        super(Item.FALL_22_PICKAXE.name());
    }

    @Override
    public Component name() {
        return Component.text("Autumn's Pickaxe")
                .color(rarity().display().color())
                .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.DIAMOND_PICKAXE)
                .display(name())
                .flags(ItemFlag.HIDE_ATTRIBUTES)
                .modelData(Model.Item.Pickaxe.FALL_22)
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.FALL_22;
    }
}
