package com.minehut.cosmetics.cosmetics.groups.companion;

import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.groups.companion.implementation.CompieCompanion;
import com.minehut.cosmetics.cosmetics.groups.companion.implementation.LatteCompanion;
import com.minehut.cosmetics.cosmetics.groups.companion.implementation.MeFollower;
import com.minehut.cosmetics.cosmetics.groups.follower.FollowerCosmetic;

import java.util.function.Supplier;

public enum Companion implements CosmeticSupplier {
    EXPLORER(CompieCompanion::new),
    LATTE_KUN(LatteCompanion::new),
    ME_BUDDY(MeFollower::new);

    private final Supplier<FollowerCosmetic> supplier;

    Companion(final Supplier<FollowerCosmetic> supplier) {
        this.supplier = supplier;
    }

    @Override
    public FollowerCosmetic get() {
        return supplier.get();
    }
}
