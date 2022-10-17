package com.minehut.cosmetics.cosmetics.groups.companion;

import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.groups.companion.implementation.*;
import com.minehut.cosmetics.cosmetics.groups.follower.FollowerCosmetic;

import java.util.function.Supplier;

public enum Companion implements CosmeticSupplier<FollowerCosmetic> {
    EXPLORER(CompieCompanion::new, true),
    LATTE_KUN(LatteCompanion::new, true),
    ME_BUDDY(MeFollower::new, false),
    WENDELL_AND_WILD(WendellAndWildCompanion::new, true),
    GHOST(GhostCompanion::new, true);

    private final Supplier<FollowerCosmetic> supplier;
    private final boolean visible;

    Companion(final Supplier<FollowerCosmetic> supplier, boolean visible) {
        this.supplier = supplier;
        this.visible = visible;
    }

    @Override
    public FollowerCosmetic get() {
        return supplier.get();
    }

    @Override
    public boolean isVisible() {
        return visible;
    }
}
