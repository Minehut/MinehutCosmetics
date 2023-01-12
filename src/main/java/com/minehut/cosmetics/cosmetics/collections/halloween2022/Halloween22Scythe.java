package com.minehut.cosmetics.cosmetics.collections.halloween2022;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Rarity;
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

public class Halloween22Scythe extends ItemCosmetic {

    public Halloween22Scythe() {
        super(Item.HALLO_22_SCYTHE.name());
    }

    @Override
    public Component name() {
        return Component.text("The Night Bringer")
                .color(rarity().display().color())
                .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.DIAMOND_SWORD)
                .display(name())
                .flags(ItemFlag.HIDE_ATTRIBUTES)
                .modelData(Model.Item.Sword.HALLO_22_SCYTHE)
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.SPOOKY_22;
    }

    @Override
    public @NotNull Rarity rarity() {
        return Rarity.RARE;
    }
}
