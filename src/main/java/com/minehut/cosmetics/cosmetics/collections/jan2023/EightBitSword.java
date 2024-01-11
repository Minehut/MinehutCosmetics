package com.minehut.cosmetics.cosmetics.collections.jan2023;

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

public class EightBitSword extends ItemCosmetic {
    public EightBitSword() {
        super(Item.EIGHT_BIT_SWORD.name());
    }

    @Override
    public Component name() {
        return Component.text("Eight Bit Sword")
                .color(rarity().display().color())
                .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.DIAMOND_SWORD)
                .display(name())
                .flags(ItemFlag.HIDE_ATTRIBUTES)
                .modelData(Model.Item.Sword.EIGHT_BIT)
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.ARCADE;
    }
}
