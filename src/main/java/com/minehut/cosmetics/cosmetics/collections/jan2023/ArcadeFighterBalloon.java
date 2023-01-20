package com.minehut.cosmetics.cosmetics.collections.jan2023;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.types.balloon.Balloon;
import com.minehut.cosmetics.cosmetics.types.balloon.BalloonCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ArcadeFighterBalloon extends BalloonCosmetic {
    public ArcadeFighterBalloon() {
        super(Balloon.ARCADE_FIGHTER.name());
    }

    @Override
    public Component name() {
        return Component.text("Arcade Fighter")
                .color(rarity().display().color())
                .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.SCUTE)
                .display(name())
                .modelData(Model.Balloon.ARCADE_FIGHTER)
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.ARCADE;
    }
}
