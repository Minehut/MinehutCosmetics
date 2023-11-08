package com.minehut.cosmetics.cosmetics.types.balloon;

import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.collections.dragoncrate.PaperDragonBalloon;
import com.minehut.cosmetics.cosmetics.collections.dragoncrate.PaperLanternBalloon;
import com.minehut.cosmetics.cosmetics.collections.halloween2022.JackOLanternBalloon;
import com.minehut.cosmetics.cosmetics.collections.jan2023.ArcadeFighterBalloon;
import com.minehut.cosmetics.cosmetics.collections.nickelodeon.DiaperBalloon;
import com.minehut.cosmetics.cosmetics.collections.nickelodeon.FlyingDutchmanBalloon;
import com.minehut.cosmetics.cosmetics.collections.nickelodeon.JellyfishBalloon;
import com.minehut.cosmetics.cosmetics.collections.nickelodeon.LoudHouseBalloon;
import com.minehut.cosmetics.cosmetics.collections.valentines2023.HeartfeltBalloon;

import java.util.function.Supplier;

public enum Balloon implements CosmeticSupplier<BalloonCosmetic> {
    JACK_O_LANTERN(JackOLanternBalloon::new),
    PAPER_DRAGON(PaperDragonBalloon::new),
    PAPER_LANTERN(PaperLanternBalloon::new),
    ARCADE_FIGHTER(ArcadeFighterBalloon::new),
    VALENTINE(HeartfeltBalloon::new),
    JELLYFISH(JellyfishBalloon::new),
    FLYING_DUTCHMAN(FlyingDutchmanBalloon::new),
    DIAPER(DiaperBalloon::new),
    LOUD_HOUSE(LoudHouseBalloon::new),

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
