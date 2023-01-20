package com.minehut.cosmetics.cosmetics.types.balloon;

import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.collections.betacrate.PaperDragonBalloon;
import com.minehut.cosmetics.cosmetics.collections.betacrate.PaperLanternBalloon;
import com.minehut.cosmetics.cosmetics.collections.halloween2022.JackOLanternBalloon;
import com.minehut.cosmetics.cosmetics.collections.jan2023.ArcadeFighterBalloon;

import java.util.function.Supplier;

public enum Balloon implements CosmeticSupplier<BalloonCosmetic> {
    JACK_O_LANTERN(JackOLanternBalloon::new),
    PAPER_DRAGON(PaperDragonBalloon::new),
    PAPER_LANTERN(PaperLanternBalloon::new),
    ARCADE_FIGHTER(ArcadeFighterBalloon::new)
    ;
    private final Supplier<BalloonCosmetic> supplier;

    Balloon(final Supplier<BalloonCosmetic> supplier) {
        this.supplier = supplier;
    }

    @Override
    public BalloonCosmetic get() {
        return supplier.get();
    }
}
