package com.minehut.cosmetics.cosmetics.groups.wing;

import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.groups.wing.implementation.BatWings;
import com.minehut.cosmetics.cosmetics.groups.wing.implementation.BlackMaidBow;
import com.minehut.cosmetics.cosmetics.groups.wing.implementation.Fall22LeafSwordWings;
import com.minehut.cosmetics.cosmetics.groups.wing.implementation.Fall22Wings;
import com.minehut.cosmetics.cosmetics.groups.wing.implementation.Halloween22ScytheBack;
import com.minehut.cosmetics.cosmetics.groups.wing.implementation.KatanaBack;
import com.minehut.cosmetics.cosmetics.groups.wing.implementation.WhiteMaidBow;

import java.util.function.Supplier;

public enum Wing implements CosmeticSupplier<WingCosmetic> {
    KATANA(KatanaBack::new, false),
    HALLO_22_SCYTHE(Halloween22ScytheBack::new, true),
    BAT(BatWings::new, true),
    BOW_WHITE(WhiteMaidBow::new, false),
    BOW_BLACK(BlackMaidBow::new, false),
    FALL_22(Fall22Wings::new, true),
    FALL_22_LEAF_SWORD(Fall22LeafSwordWings::new, true);

    private final Supplier<WingCosmetic> supplier;
    private final boolean visible;

    Wing(final Supplier<WingCosmetic> supplier, boolean visible) {
        this.supplier = supplier;
        this.visible = visible;
    }

    @Override
    public WingCosmetic get() {
        return supplier.get();
    }

    @Override
    public boolean isVisible() {
        return visible;
    }
}
