package com.minehut.cosmetics.cosmetics.collections.betacrate;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Rarity;
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

public class TurtleHat extends HatCosmetic {

    public TurtleHat() {
        super(Hat.TURTLE.name());
    }

    @Override
    public Component name() {
        return Component.text("Turtle Hat")
                .color(rarity().display().color())
                .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.DRAGON_CRATE;
    }

    @Override
    public @NotNull Rarity rarity() {
        return Rarity.RARE;
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.DIAMOND_LEGGINGS)
                .display(name())
                .modelData(Model.Hat.TURTLE)
                .flags(ItemFlag.HIDE_ATTRIBUTES)
                .build();
    }
}
