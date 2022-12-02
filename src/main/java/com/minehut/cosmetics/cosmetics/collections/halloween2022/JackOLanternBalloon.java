package com.minehut.cosmetics.cosmetics.collections.halloween2022;

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

public class JackOLanternBalloon extends BalloonCosmetic {
    public JackOLanternBalloon() {
        super(Balloon.JACK_O_LANTERN.name());
    }

    @Override
    public Component name() {
        return Component.text("Jack-o-Lantern Balloon")
                .decoration(TextDecoration.ITALIC, false)
                .color(rarity().display().color());
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.SCUTE)
                .display(name())
                .modelData(Model.Balloon.JACK_O_LANTERN)
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.SPOOKY_22;
    }
}