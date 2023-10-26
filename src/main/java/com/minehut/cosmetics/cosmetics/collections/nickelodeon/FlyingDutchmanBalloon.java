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

public class FlyingDutchmanBalloon extends BalloonCosmetic {
    public FlyingDutchmanBalloon() {
        super(Balloon.FLYING_DUTCHMAN.name());
    }

    @Override
    public Component name() {
        return Component.text("Flying Dutchman Balloon")
            .color(rarity().display().color())
            .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.SCUTE)
            .display(name())
            .modelData(Model.Balloon.FLYING_DUTCHMAN)
            .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.NICK_ESCAPE_SEASON_1;
    }

    @Override
    public @NotNull Rarity rarity() {
        return Rarity.UNCOMMON;
    }
}
