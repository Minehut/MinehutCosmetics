package com.minehut.cosmetics.cosmetics.groups.item;

import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.axe.Fall2022Axe;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.pickaxe.ExplorerPickaxe;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.pickaxe.Fall2022Pickaxe;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.shield.Fall2022Shield;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.shovel.Fall2022Shovel;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.shovel.GraveShovel;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.spyglass.ExplorerSpyglass;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.sword.Fall2022Sword;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.sword.ExplorerSword;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.sword.Katana;

import java.util.function.Supplier;

public enum Item implements CosmeticSupplier<ItemCosmetic> {
    EXPLORER_SPYGLASS(ExplorerSpyglass::new, true),
    // pickaxes
    EXPLORER_PICKAXE(ExplorerPickaxe::new, true),
    FALL_22_PICKAXE(Fall2022Pickaxe::new, true),
    // swords
    EXPLORER_SWORD(ExplorerSword::new, true),
    FALL_22_SWORD(Fall2022Sword::new, true),
    KATANA(Katana::new, false),
    // shovels
    FALL_22_SHOVEL(Fall2022Shovel::new, true),
    GRAVE_SHOVEL(GraveShovel::new, true),
    // axe
    FALL_22_AXE(Fall2022Axe::new, true),
    // shields
    FALL_22_SHIELD(Fall2022Shield::new, true);

    private final Supplier<ItemCosmetic> supplier;
    private final boolean visible;

    Item(final Supplier<ItemCosmetic> supplier, boolean visible) {
        this.supplier = supplier;
        this.visible = visible;
    }

    @Override
    public ItemCosmetic get() {
        return supplier.get();
    }

    @Override
    public boolean isVisible() {
        return visible;
    }
}
