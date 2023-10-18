package com.minehut.cosmetics.cosmetics.collections.nickelodeon;


import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Rarity;
import com.minehut.cosmetics.cosmetics.types.companion.Companion;
import com.minehut.cosmetics.cosmetics.types.companion.CompanionCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class ReptarCompanion extends CompanionCosmetic {
    public ReptarCompanion() {
        super(
            Companion.REPTAR.name(),
            new Vector(0, -1, 0),
            true,
            true,
            false
        );
    }

    @Override
    public Component name() {
        return Component.text("Reptar")
            .decoration(TextDecoration.ITALIC, false)
            .color(rarity().display().color());
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.SCUTE)
            .display(name())
            .modelData(Model.Companion.REPTAR)
            .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.NICK_GOLF_SEASON_1;
    }

    @Override
    public @NotNull Rarity rarity() {
        return Rarity.UNCOMMON;
    }
}
