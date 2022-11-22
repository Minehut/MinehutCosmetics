package com.minehut.cosmetics.cosmetics.collections.betacrate;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
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

import java.util.function.Supplier;

public class SteampunkHat extends HatCosmetic {
    public SteampunkHat() {
        super(Hat.STEAMPUNK.name());
    }

    @Override
    public Component name() {
        return Component.text("Steampunk Hat")
                .color(rarity().display().color())
                .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.DIAMOND_LEGGINGS)
                .modelData(Model.Hat.STEAMPUNK)
                .display(name())
                .flags(ItemFlag.HIDE_ATTRIBUTES)
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.DRAGON_CRATE;
    }
}
