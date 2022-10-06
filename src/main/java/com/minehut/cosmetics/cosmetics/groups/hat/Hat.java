package com.minehut.cosmetics.cosmetics.groups.hat;


import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.groups.hat.implementation.ExplorerHat;

import java.util.function.Supplier;

public enum Hat implements CosmeticSupplier {
    EXPLORER(ExplorerHat::new);
    private final Supplier<HatCosmetic> supplier;

    Hat(final Supplier<HatCosmetic> supplier) {
        this.supplier = supplier;
    }

    @Override
    public HatCosmetic get() {
        return supplier.get();
    }
}
