package com.minehut.cosmetics.cosmetics.collections.dragoncrate;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.Rarity;
import com.minehut.cosmetics.cosmetics.types.wing.Wing;
import com.minehut.cosmetics.cosmetics.types.wing.WingCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PegasusWings extends WingCosmetic {
    public PegasusWings() {
        super(Wing.PEGASUS.name());
    }

    @Override
    public Permission visibility() {
        return Permission.collectionIsActive(Collection.DRAGON_CRATE);
    }

    @Override
    public Component name() {
        return Component.text("Small Pegasus Wings")
                .color(rarity().display().color())
                .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.SCUTE)
                .modelData(Model.Wing.PEGASUS)
                .display(name())
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.DRAGON_CRATE;
    }

    @Override
    public @NotNull Rarity rarity() {
        return Rarity.RARE;
    }
}
