package com.minehut.cosmetics.cosmetics.groups.wing.implementation;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticPermission;
import com.minehut.cosmetics.cosmetics.groups.item.Item;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.sword.Katana;
import com.minehut.cosmetics.cosmetics.groups.wing.WingCosmetic;
import org.bukkit.inventory.ItemStack;

public class KatanaBack extends WingCosmetic {

    public KatanaBack() {
        super(Item.KATANA.name(),
                Katana.NAME,
                CosmeticPermission.hasPurchased(CosmeticCategory.ITEM.name(), Item.KATANA.name()),
                p -> Katana.ITEM.get());
    }

    @Override
    public ItemStack menuIcon() {
        return Katana.ITEM.get();
    }
}