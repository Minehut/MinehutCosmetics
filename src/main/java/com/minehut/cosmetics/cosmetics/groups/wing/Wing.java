package com.minehut.cosmetics.cosmetics.groups.wing;

import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.groups.wing.implementation.KatanaBack;

import java.util.function.Supplier;

public enum Wing implements CosmeticSupplier<WingCosmetic> {
    KATANA(KatanaBack::new, false)
    ;

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
