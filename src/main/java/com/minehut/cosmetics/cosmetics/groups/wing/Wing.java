package com.minehut.cosmetics.cosmetics.groups.wing;

import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.groups.wing.implementation.BatWings;
import com.minehut.cosmetics.cosmetics.groups.wing.implementation.BlackMaidBow;
import com.minehut.cosmetics.cosmetics.groups.wing.implementation.Fall22LeafSwordWings;
import com.minehut.cosmetics.cosmetics.groups.wing.implementation.Fall22Wings;
import com.minehut.cosmetics.cosmetics.groups.wing.implementation.Halloween22ScytheBack;
import com.minehut.cosmetics.cosmetics.groups.wing.implementation.KatanaBack;
import com.minehut.cosmetics.cosmetics.groups.wing.implementation.WhiteMaidBow;

import java.util.function.Supplier;

public enum Wing implements CosmeticSupplier<WingCosmetic> {
    KATANA(KatanaBack::new),
    HALLO_22_SCYTHE(Halloween22ScytheBack::new),
    BAT(BatWings::new),
    BOW_WHITE(WhiteMaidBow::new),
    BOW_BLACK(BlackMaidBow::new),
    FALL_22(Fall22Wings::new),
    FALL_22_LEAF_SWORD(Fall22LeafSwordWings::new);

    private final Supplier<WingCosmetic> supplier;

    Wing(final Supplier<WingCosmetic> supplier) {
        this.supplier = supplier;
    }

    @Override
    public WingCosmetic get() {
        return supplier.get();
    }
}
