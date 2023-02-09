package com.minehut.cosmetics.cosmetics.collections.valentines2023;


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

public class HeartfeltCompanion extends CompanionCosmetic {
    public HeartfeltCompanion() {
        super(
            Companion.VALENTINE_PET.name(),
            new Vector(0, -1, 0),
            true,
            true,
            false
        );
    }

    @Override
    public Component name() {
        return Component.text("Heartfelt Pet")
            .decoration(TextDecoration.ITALIC, false)
            .color(rarity().display().color());
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.SCUTE)
            .display(name())
            .modelData(Model.Companion.VALENTINE_PET)
            .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.VALENTINES_2023;
    }

    @Override
    public @NotNull Rarity rarity() {
        return Rarity.EPIC;
    }
}
