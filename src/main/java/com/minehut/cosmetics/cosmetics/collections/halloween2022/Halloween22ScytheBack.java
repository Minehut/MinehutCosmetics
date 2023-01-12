package com.minehut.cosmetics.cosmetics.collections.halloween2022;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.wing.Wing;
import com.minehut.cosmetics.cosmetics.types.wing.WingCosmetic;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Halloween22ScytheBack extends WingCosmetic {

    private final Halloween22Scythe scythe = new Halloween22Scythe();

    public Halloween22ScytheBack() {
        super(Wing.HALLO_22_SCYTHE.name());
    }

    @Override
    public Permission permission() {
        return scythe.permission();
    }

    @Override
    public Permission visibility() {
        return scythe.visibility();
    }

    @Override
    public Component name() {
        return scythe.name();
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return scythe.menuIcon();
    }

    @Override
    public @NotNull Collection collection() {
        return scythe.collection();
    }
}