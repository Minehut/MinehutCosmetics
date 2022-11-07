package com.minehut.cosmetics.cosmetics.groups.wing.implementation;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.groups.item.Item;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.sword.Halloween22Scythe;
import com.minehut.cosmetics.cosmetics.groups.wing.Wing;
import com.minehut.cosmetics.cosmetics.groups.wing.WingCosmetic;
import org.bukkit.inventory.ItemStack;

public class Halloween22ScytheBack extends WingCosmetic {

    public Halloween22ScytheBack() {
        super(Wing.HALLO_22_SCYTHE.name(),
                Halloween22Scythe.NAME,
                Permission.hasPurchased(CosmeticCategory.ITEM.name(), Item.HALLO_22_SCYTHE.name()),
                Permission.deny());
    }

    @Override
    public ItemStack menuIcon() {
        return Halloween22Scythe.ITEM.get();
    }
}