package com.minehut.cosmetics.cosmetics.collections.autumn2022;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.wing.Wing;
import com.minehut.cosmetics.cosmetics.types.wing.WingCosmetic;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Fall22LeafSwordWings extends WingCosmetic {

    private final Fall22LeafSword sword = new Fall22LeafSword();

    public Fall22LeafSwordWings() {
        super(Wing.FALL_22_LEAF_SWORD.name());
    }

    @Override
    public Permission permission() {
        return sword.permission();
    }

    @Override
    public Permission visibility() {
        return sword.visibility();
    }

    @Override
    public Component name() {
        return sword.name();
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return sword.menuIcon();
    }

    @Override
    public @NotNull Collection collection() {
        return sword.collection();
    }
}