package com.minehut.cosmetics.cosmetics.groups.balloon;

import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticSupplier;

import java.util.function.Supplier;

public enum Balloon implements CosmeticSupplier {
    ;
    private final Supplier<BalloonCosmetic> supplier;

    Balloon(final Supplier<BalloonCosmetic> supplier) {
        this.supplier = supplier;
    }

    @Override
    public Cosmetic get() {
        return supplier.get();
    }
}
