package com.minehut.cosmetics.cosmetics.groups.item;

import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.axe.Fall2022Axe;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.pickaxe.ExplorerPickaxe;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.pickaxe.Fall2022Pickaxe;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.shield.Fall2022Shield;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.shovel.Fall2022Shovel;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.shovel.GraveShovel;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.spyglass.ExplorerSpyglass;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.sword.ExplorerSword;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.sword.Fall2022Sword;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.sword.Halloween22Scythe;
import com.minehut.cosmetics.cosmetics.groups.item.implementation.sword.Katana;

import java.util.function.Supplier;

public enum Item implements CosmeticSupplier<ItemCosmetic> {
    // Explorer Set
    EXPLORER_SPYGLASS(ExplorerSpyglass::new, true),
    EXPLORER_PICKAXE(ExplorerPickaxe::new, true),
    EXPLORER_SWORD(ExplorerSword::new, true),
    // Fall Set
    FALL_22_PICKAXE(Fall2022Pickaxe::new, true),
    FALL_22_SHOVEL(Fall2022Shovel::new, true),
    FALL_22_SWORD(Fall2022Sword::new, true),
    FALL_22_AXE(Fall2022Axe::new, true),
    // shields
    FALL_22_SHIELD(Fall2022Shield::new, true),
    // Spooktacular
    GRAVE_SHOVEL(GraveShovel::new, true),
    HALLO_22_SCYTHE(Halloween22Scythe::new, true),
    // special
    KATANA(Katana::new, false),
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
