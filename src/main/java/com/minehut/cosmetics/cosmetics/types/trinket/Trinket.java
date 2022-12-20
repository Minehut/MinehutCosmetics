package com.minehut.cosmetics.cosmetics.types.trinket;


import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.collections.netflix2022.SpooktacularBoombox;
import com.minehut.cosmetics.cosmetics.collections.winter2022.IceScepterTrinket;

import java.util.function.Supplier;

public enum Trinket implements CosmeticSupplier<TrinketCosmetic> {
    SPOOKTACULAR_BOOMBOX(SpooktacularBoombox::new),
    ICE_SCEPTER(IceScepterTrinket::new);

    private final Supplier<TrinketCosmetic> supplier;

    Trinket(final Supplier<TrinketCosmetic> supplier) {
        this.supplier = supplier;
    }

    @Override
    public TrinketCosmetic get() {
        return supplier.get();
    }
}
