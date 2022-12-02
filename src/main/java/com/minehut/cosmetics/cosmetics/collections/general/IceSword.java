package com.minehut.cosmetics.cosmetics.collections.general;

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

public class IceSword extends ItemCosmetic {
    public IceSword() {
        super(Item.ICE_SWORD.name());
    }

    @Override
    public Component name() {
        return Component.text("Ice Sword")
                .color(rarity().display().color())
                .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.DIAMOND_SWORD)
                .display(name())
                .flags(ItemFlag.HIDE_ATTRIBUTES)
                .modelData(Model.Item.Sword.ICE_SWORD)
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.ICE;
    }
}

