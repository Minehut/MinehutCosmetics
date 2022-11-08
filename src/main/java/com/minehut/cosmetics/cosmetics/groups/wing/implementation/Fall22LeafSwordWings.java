package com.minehut.cosmetics.cosmetics.groups.wing.implementation;

import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.sword.Fall22LeafSword;
import com.minehut.cosmetics.cosmetics.groups.wing.Wing;
import com.minehut.cosmetics.cosmetics.groups.wing.WingCosmetic;
import org.bukkit.inventory.ItemStack;

public class Fall22LeafSwordWings extends WingCosmetic {

    public Fall22LeafSwordWings() {
        super(Wing.FALL_22_LEAF_SWORD.name(), Fall22LeafSword.NAME);
    }

    @Override
    public Permission permission() {
        return Fall22LeafSword.PERMISSION;
    }

    @Override
    public Permission visibility() {
        return Fall22LeafSword.VISIBILITY;
    }

    @Override
    public ItemStack menuIcon() {
        return Fall22LeafSword.ITEM.get();
    }
}