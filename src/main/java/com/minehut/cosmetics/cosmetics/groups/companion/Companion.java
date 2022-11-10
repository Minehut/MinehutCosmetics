package com.minehut.cosmetics.cosmetics.groups.companion;

import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.groups.companion.implementation.BearCompanion;
import com.minehut.cosmetics.cosmetics.groups.companion.implementation.CompieCompanion;
import com.minehut.cosmetics.cosmetics.groups.companion.implementation.GhostCompanion;
import com.minehut.cosmetics.cosmetics.groups.companion.implementation.GoldFishCompanion;
import com.minehut.cosmetics.cosmetics.groups.companion.implementation.GreenTurtleCompanion;
import com.minehut.cosmetics.cosmetics.groups.companion.implementation.KittenCompanion;
import com.minehut.cosmetics.cosmetics.groups.companion.implementation.LatteCompanion;
import com.minehut.cosmetics.cosmetics.groups.companion.implementation.MeFollower;
import com.minehut.cosmetics.cosmetics.groups.companion.implementation.RedRobinCompanion;
import com.minehut.cosmetics.cosmetics.groups.companion.implementation.WendellAndWildCompanion;
import com.minehut.cosmetics.cosmetics.groups.follower.FollowerCosmetic;

import java.util.function.Supplier;

public enum Companion implements CosmeticSupplier<FollowerCosmetic> {
    EXPLORER(CompieCompanion::new),
    LATTE_KUN(LatteCompanion::new),
    ME_BUDDY(MeFollower::new),
    WENDELL_AND_WILD(WendellAndWildCompanion::new),
    GHOST(GhostCompanion::new),
    GREEN_TURTLE(GreenTurtleCompanion::new),
    GOLD_FISH(GoldFishCompanion::new),
    KITTEN(KittenCompanion::new),
    BEAR(BearCompanion::new),
    RED_ROBIN(RedRobinCompanion::new),
    ;

    private final Supplier<FollowerCosmetic> supplier;

    Companion(final Supplier<FollowerCosmetic> supplier) {
        this.supplier = supplier;
    }

    @Override
    public FollowerCosmetic get() {
        return supplier.get();
    }
}
