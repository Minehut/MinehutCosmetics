package com.minehut.cosmetics.cosmetics.groups.hat;


import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.groups.hat.implementation.CatEars;
import com.minehut.cosmetics.cosmetics.groups.hat.implementation.DevilHorns;
import com.minehut.cosmetics.cosmetics.groups.hat.implementation.ExplorerHat;
import com.minehut.cosmetics.cosmetics.groups.hat.implementation.FoxEars;
import com.minehut.cosmetics.cosmetics.groups.hat.implementation.WitchHat;
import com.minehut.cosmetics.cosmetics.groups.hat.implementation.spooktacular2022.MouseEars;

import java.util.function.Supplier;

public enum Hat implements CosmeticSupplier<HatCosmetic> {
    // explorer
    EXPLORER(ExplorerHat::new, true),
    // halloween
    WITCH(WitchHat::new, true),
    CAT_EARS(CatEars::new, true),
    DEVIL_HORNS(DevilHorns::new, true),
    FOX_EARS(FoxEars::new, true),
    MOUSE_EARS(MouseEars::new, true);

    private final Supplier<HatCosmetic> supplier;
    private final boolean visible;

    /**
     * @param supplier supplier for cosmetic
     * @param visible  whether the cosmetic is visible
     */
    Hat(final Supplier<HatCosmetic> supplier, boolean visible) {
        this.supplier = supplier;
        this.visible = visible;
    }

    @Override
    public HatCosmetic get() {
        return supplier.get();
    }

    @Override
    public boolean isVisible() {
        return visible;
    }
}
