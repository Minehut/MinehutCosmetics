package com.minehut.cosmetics.cosmetics.groups.balloon;

import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.groups.balloon.implementation.JackOLanternBalloon;

import java.util.function.Supplier;

public enum Balloon implements CosmeticSupplier<BalloonCosmetic> {
    JACK_O_LANTERN(JackOLanternBalloon::new, true)
    ;
    private final Supplier<BalloonCosmetic> supplier;
    private final boolean visible;

    Balloon(final Supplier<BalloonCosmetic> supplier, boolean visible) {
        this.supplier = supplier;
        this.visible = visible;
    }

    @Override
    public BalloonCosmetic get() {
        return supplier.get();
    }

    @Override
    public boolean isVisible() {
        return visible;
    }
}
