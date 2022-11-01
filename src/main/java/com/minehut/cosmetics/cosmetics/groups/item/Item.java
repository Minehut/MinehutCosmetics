package com.minehut.cosmetics.cosmetics.groups.item;

import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.arrow.Fall22Arrow;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.axe.Fall2022Axe;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.bow.Fall22Bow;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.pickaxe.ExplorerPickaxe;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.pickaxe.Fall22Pickaxe;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.shield.Fall2022Shield;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.shovel.Fall22Shovel;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.shovel.GraveShovel;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.spyglass.ExplorerSpyglass;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.sword.ExplorerSword;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.sword.Fall22LeafSword;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.sword.Fall22Sword;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.sword.Halloween22Scythe;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.sword.Katana;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.sword.MaidDuster;

import java.util.function.Supplier;

public enum Item implements CosmeticSupplier<ItemCosmetic> {
    // Explorer Set
    EXPLORER_SPYGLASS(ExplorerSpyglass::new, true),
    EXPLORER_PICKAXE(ExplorerPickaxe::new, true),
    EXPLORER_SWORD(ExplorerSword::new, true),
    // Fall Set
    FALL_22_PICKAXE(Fall22Pickaxe::new, true),
    FALL_22_SHOVEL(Fall22Shovel::new, true),
    FALL_22_SWORD(Fall22Sword::new, true),
    FALL_22_LEAF_SWORD(Fall22LeafSword::new, true),

    FALL_22_AXE(Fall2022Axe::new, true),
    FALL_22_ARROW(Fall22Arrow::new, true),
    FALL_22_BOW(Fall22Bow::new, true),
    // shields
    FALL_22_SHIELD(Fall2022Shield::new, true),
    // Spooktacular
    GRAVE_SHOVEL(GraveShovel::new, true),
    HALLO_22_SCYTHE(Halloween22Scythe::new, true),
    // special
    KATANA(Katana::new, false),
    // maid
    MAID_DUSTER(MaidDuster::new, false),

    ;
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
