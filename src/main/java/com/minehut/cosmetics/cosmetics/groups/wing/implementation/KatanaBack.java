package com.minehut.cosmetics.cosmetics.groups.wing.implementation;

import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.groups.item.Item;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.sword.Katana;
import com.minehut.cosmetics.cosmetics.groups.wing.WingCosmetic;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class KatanaBack extends WingCosmetic {

    public KatanaBack() {
        super(Item.KATANA.name(),
                Katana.NAME,
                Permission.uuid(UUID.fromString("0cab222b-63ce-46c0-ada3-a56365f2dc8a")),
                Permission.deny());
    }

    @Override
    public ItemStack menuIcon() {
        return Katana.ITEM.get();
    }
}