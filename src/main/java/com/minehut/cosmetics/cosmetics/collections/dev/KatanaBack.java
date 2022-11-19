package com.minehut.cosmetics.cosmetics.collections.dev;

import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.item.Item;
import com.minehut.cosmetics.cosmetics.types.wing.WingCosmetic;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class KatanaBack extends WingCosmetic {

    public KatanaBack() {
        super(Item.KATANA.name(), Katana.NAME);
    }

    @Override
    public Permission permission() {
        return Permission.uuid(UUID.fromString("0cab222b-63ce-46c0-ada3-a56365f2dc8a"));
    }

    @Override
    public Permission visibility() {
        return Permission.deny();
    }

    @Override
    public ItemStack menuIcon() {
        return Katana.ITEM.get();
    }
}