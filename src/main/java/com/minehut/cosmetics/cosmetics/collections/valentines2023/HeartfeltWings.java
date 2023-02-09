package com.minehut.cosmetics.cosmetics.collections.valentines2023;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Rarity;
import com.minehut.cosmetics.cosmetics.types.wing.Wing;
import com.minehut.cosmetics.cosmetics.types.wing.WingCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class HeartfeltWings extends WingCosmetic {
    public HeartfeltWings() {
        super(Wing.VALENTINE_WINGS.name());

    }

    @Override
    public Component name() {
        return Component.text("Heartfelt Wings")
            .color(NamedTextColor.GOLD)
            .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.SCUTE)
            .modelData(Model.Wing.VALENTINE_WINGS)
            .display(name())
            .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.VALENTINES_2023;
    }

    @Override
    public @NotNull Rarity rarity() {
        return Rarity.LEGENDARY;
    }
}
