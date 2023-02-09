package com.minehut.cosmetics.cosmetics.collections.valentines2023;

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

public class HeartfeltBalloon extends BalloonCosmetic {
    public HeartfeltBalloon() {
        super(Balloon.VALENTINE.name());
    }

    @Override
    public Component name() {
        return Component.text("Heartfelt Balloon")
            .color(rarity().display().color())
            .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.SCUTE)
            .display(name())
            .modelData(Model.Balloon.VALENTINE)
            .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.VALENTINES_2023;
    }

    @Override
    public @NotNull Rarity rarity() {
        return Rarity.UNCOMMON;
    }
}
