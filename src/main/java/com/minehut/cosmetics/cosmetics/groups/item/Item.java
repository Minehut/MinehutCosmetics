package com.minehut.cosmetics.cosmetics.groups.item;

import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.axe.Fall2022Axe;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.pickaxe.ExplorerPickaxe;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.pickaxe.Fall2022Pickaxe;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.shield.Fall2022Shield;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.shovel.Fall2022Shovel;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.spyglass.ExplorerSpyglass;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.sword.Fall2022Sword;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.sword.ExplorerSword;

import java.util.function.Supplier;

public enum Item implements CosmeticSupplier {
    EXPLORER_SPYGLASS(ExplorerSpyglass::new),
    // pickaxes
    EXPLORER_PICKAXE(ExplorerPickaxe::new),
    FALL_22_PICKAXE(Fall2022Pickaxe::new),
    // swords
    EXPLORER_SWORD(ExplorerSword::new),
    FALL_22_SWORD(Fall2022Sword::new),
    // shovels
    FALL_22_SHOVEL(Fall2022Shovel::new),
    // axe
    FALL_22_AXE(Fall2022Axe::new),
    // shields
    FALL_22_SHIELD(Fall2022Shield::new);

    private final Supplier<ItemCosmetic> supplier;

    Item(final Supplier<ItemCosmetic> supplier) {
        this.supplier = supplier;
    }

    @Override
    public ItemCosmetic get() {
        return supplier.get();
    }
}
