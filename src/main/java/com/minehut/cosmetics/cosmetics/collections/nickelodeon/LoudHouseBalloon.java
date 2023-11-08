package com.minehut.cosmetics.cosmetics.collections.nickelodeon;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Rarity;
import com.minehut.cosmetics.cosmetics.types.balloon.Balloon;
import com.minehut.cosmetics.cosmetics.types.balloon.BalloonCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class LoudHouseBalloon extends BalloonCosmetic {
    public LoudHouseBalloon() {
        super(Balloon.LOUD_HOUSE.name());
    }

    @Override
    public Component name() {
        return Component.text("Loud House Balloon")
            .color(rarity().display().color())
            .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.SCUTE)
            .display(name())
            .modelData(Model.Balloon.LOUD_HOUSE)
            .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.NICK_GOLF_SEASON_2;
    }

    @Override
    public @NotNull Rarity rarity() {
        return Rarity.UNCOMMON;
    }
}
