package com.minehut.cosmetics.cosmetics.collections.maid;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.types.wing.Wing;
import com.minehut.cosmetics.cosmetics.types.wing.WingCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BlackMaidBow extends WingCosmetic {

    public BlackMaidBow() {
        super(Wing.BOW_BLACK.name());
    }

    @Override
    public Component name() {
        return Component.text("Maid's Black Bow")
                .color(rarity().display().color())
                .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.SCUTE)
                .display(name())
                .modelData(Model.Wing.BOW_BLACk)
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.MAID;
    }
}