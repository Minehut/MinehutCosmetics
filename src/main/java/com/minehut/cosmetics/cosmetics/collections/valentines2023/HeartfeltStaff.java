package com.minehut.cosmetics.cosmetics.collections.valentines2023;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Rarity;
import com.minehut.cosmetics.cosmetics.types.trinket.ItemTrinket;
import com.minehut.cosmetics.cosmetics.types.trinket.Trinket;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class HeartfeltStaff extends ItemTrinket {
    public HeartfeltStaff() {
        super(Trinket.VALENTINE_STAFF.name());
    }

    @Override
    public Component name() {
        return Component.text("Heartfelt Staff")
            .decoration(TextDecoration.ITALIC, false)
            .color(rarity().display().color());
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.SCUTE)
            .display(name())
            .modelData(Model.Trinket.VALENTINE_STAFF)
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
