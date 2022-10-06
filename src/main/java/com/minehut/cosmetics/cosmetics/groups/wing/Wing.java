package com.minehut.cosmetics.cosmetics.groups.wing;

import com.minehut.cosmetics.cosmetics.CosmeticSupplier;

import java.util.function.Supplier;

public enum Wing implements CosmeticSupplier {
    ;

    private final Supplier<WingCosmetic> supplier;

    Wing(final Supplier<WingCosmetic> supplier) {
        this.supplier = supplier;
    }

    @Override
    public WingCosmetic get() {
        return supplier.get();
    }
}
