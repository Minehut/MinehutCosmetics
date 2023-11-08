package com.minehut.cosmetics.cosmetics.collections.nickelodeon;

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

public class MagicConchTrinket extends ItemTrinket {
    public MagicConchTrinket() {
        super(Trinket.MAGIC_CONCH.name());
    }

    @Override
    public Component name() {
        return Component.text("Magic Conch")
            .decoration(TextDecoration.ITALIC, false)
            .color(rarity().display().color());
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.SCUTE)
            .display(name())
            .modelData(Model.Trinket.MAGIC_CONCH)
            .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.NICK_GOLF_SEASON_2;
    }

    @Override
    public @NotNull Rarity rarity() {
        return Rarity.EPIC;
    }
}
