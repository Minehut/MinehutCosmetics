package com.minehut.cosmetics.cosmetics.groups.trinket;


import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.groups.trinket.implementation.SpooktacularBoombox;

import java.util.function.Supplier;

public enum Trinket implements CosmeticSupplier<TrinketCosmetic> {
    SPOOKTACULAR_BOOMBOX(SpooktacularBoombox::new, true)
    ;

    private final Supplier<TrinketCosmetic> supplier;
    private final boolean visible;
    Trinket(final Supplier<TrinketCosmetic> supplier, boolean visible) {
        this.supplier = supplier;
        this.visible = visible;
    }

    @Override
    public TrinketCosmetic get() {
        return supplier.get();
    }

    @Override
    public boolean isVisible() {
        return visible;
    }
}
