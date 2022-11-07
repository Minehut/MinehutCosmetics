package com.minehut.cosmetics.cosmetics.groups.item;

import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.arrow.Fall22Arrow;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.axe.Fall2022Axe;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.bow.Fall22Bow;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.pickaxe.ExplorerPickaxe;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.pickaxe.Fall22Pickaxe;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.shield.Fall2022Shield;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.shovel.Fall22Shovel;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.shovel.GraveShovel;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.spyglass.ExplorerSpyglass;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.sword.CrusaderSword;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.sword.ExplorerSword;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.sword.Fall22LeafSword;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.sword.Fall22Sword;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.sword.Halloween22Scythe;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.sword.Katana;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.sword.MaidDuster;

import java.util.function.Supplier;

public enum Item implements CosmeticSupplier<ItemCosmetic> {
    // Explorer Set
    EXPLORER_SPYGLASS(ExplorerSpyglass::new),
    EXPLORER_PICKAXE(ExplorerPickaxe::new),
    EXPLORER_SWORD(ExplorerSword::new),
    // Fall Set
    FALL_22_PICKAXE(Fall22Pickaxe::new),
    FALL_22_SHOVEL(Fall22Shovel::new),
    FALL_22_SWORD(Fall22Sword::new),
    FALL_22_LEAF_SWORD(Fall22LeafSword::new),

    FALL_22_AXE(Fall2022Axe::new),
    FALL_22_ARROW(Fall22Arrow::new),
    FALL_22_BOW(Fall22Bow::new),
    // shields
    FALL_22_SHIELD(Fall2022Shield::new),
    // Spooktacular
    GRAVE_SHOVEL(GraveShovel::new),
    HALLO_22_SCYTHE(Halloween22Scythe::new),
    // special
    KATANA(Katana::new),
    // maid
    MAID_DUSTER(MaidDuster::new),
    // Crusader Collection
    CRUSADER_SWORD(CrusaderSword::new),
    ;

    private final Supplier<ItemCosmetic> supplier;

    Item(final Supplier<ItemCosmetic> supplier) {
        this.supplier = supplier;
    }

    @Override
    public ItemCosmetic get() {
        return supplier.get();
    }
}
