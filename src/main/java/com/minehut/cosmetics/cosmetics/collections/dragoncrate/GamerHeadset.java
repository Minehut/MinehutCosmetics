package com.minehut.cosmetics.cosmetics.collections.dragoncrate;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Rarity;
import com.minehut.cosmetics.cosmetics.types.hat.Hat;
import com.minehut.cosmetics.cosmetics.types.hat.HatCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GamerHeadset extends HatCosmetic {

    public GamerHeadset() {
        super(Hat.GAMER_HEADSET.name());
    }

    @Override
    public Component name() {
        return Component.text("Pink Gamer Headset")
                .color(rarity().display().color())
                .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.DRAGON_CRATE;
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.DIAMOND_LEGGINGS)
                .modelData(Model.Hat.GAMER_HEADSET)
                .display(name())
                .flags(ItemFlag.HIDE_ATTRIBUTES)
                .build();
    }

    @Override
    public @NotNull Rarity rarity() {
        return Rarity.RARE;
    }
}
