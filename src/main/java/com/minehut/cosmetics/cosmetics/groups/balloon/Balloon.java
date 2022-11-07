package com.minehut.cosmetics.cosmetics.groups.balloon;

import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.groups.balloon.implementation.JackOLanternBalloon;

import java.util.function.Supplier;

public enum Balloon implements CosmeticSupplier<BalloonCosmetic> {
    JACK_O_LANTERN(JackOLanternBalloon::new);
    private final Supplier<BalloonCosmetic> supplier;

    Balloon(final Supplier<BalloonCosmetic> supplier) {
        this.supplier = supplier;
    }

    @Override
    public BalloonCosmetic get() {
        return supplier.get();
    }
}
