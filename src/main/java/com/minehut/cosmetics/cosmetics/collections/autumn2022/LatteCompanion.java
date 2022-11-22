package com.minehut.cosmetics.cosmetics.collections.autumn2022;


import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.types.companion.CompanionCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class LatteCompanion extends CompanionCosmetic {

    public LatteCompanion() {
        super(
                com.minehut.cosmetics.cosmetics.types.companion.Companion.LATTE_KUN.name(),
                new Vector(0, -1, 0),
                true,
                true,
                false
        );
    }

    @Override
    public Component name() {
        return Component.text("Latte-kun")
                .decoration(TextDecoration.ITALIC, false)
                .color(rarity().display().color());
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.SCUTE)
                .display(name())
                .modelData(Model.Companion.LATTE_KUN)
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.FALL_22;
    }
}
