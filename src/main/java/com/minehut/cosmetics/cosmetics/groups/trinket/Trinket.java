package com.minehut.cosmetics.cosmetics.groups.trinket;


import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.groups.trinket.implementation.SpooktacularBoombox;

import java.util.function.Supplier;

public enum Trinket implements CosmeticSupplier<TrinketCosmetic> {
    SPOOKTACULAR_BOOMBOX(SpooktacularBoombox::new);

    private final Supplier<TrinketCosmetic> supplier;

    Trinket(final Supplier<TrinketCosmetic> supplier) {
        this.supplier = supplier;
    }

    @Override
    public TrinketCosmetic get() {
        return supplier.get();
    }
}
