package com.minehut.cosmetics.cosmetics.collections.nickelodeon;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Rarity;
import com.minehut.cosmetics.cosmetics.types.hat.Hat;
import com.minehut.cosmetics.cosmetics.types.hat.HatCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EarthKingdomHat extends HatCosmetic {

    public EarthKingdomHat() {
        super(Hat.EARTH_KINGDOM_HAT.name());
    }

    @Override
    public Component name() {
        return Component.text("Earth Kingdom Hat")
            .color(NamedTextColor.YELLOW)
            .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.DIAMOND_LEGGINGS)
            .display(name())
            .flags(ItemFlag.HIDE_ATTRIBUTES)
            .modelData(Model.Hat.EARTH_KINGDOM_HAT)
            .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.NICK_GOLF_SEASON_3;
    }

    @Override
    public @NotNull Rarity rarity() {
        return Rarity.UNCOMMON;
    }
}
