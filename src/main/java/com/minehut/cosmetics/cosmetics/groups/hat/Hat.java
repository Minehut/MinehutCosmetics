package com.minehut.cosmetics.cosmetics.groups.hat;


import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.groups.hat.implementation.CatEars;
import com.minehut.cosmetics.cosmetics.groups.hat.implementation.DevilHorns;
import com.minehut.cosmetics.cosmetics.groups.hat.implementation.Fall22Hat;
import com.minehut.cosmetics.cosmetics.groups.hat.implementation.FoxEars;
import com.minehut.cosmetics.cosmetics.groups.hat.implementation.WitchHat;
import com.minehut.cosmetics.cosmetics.groups.hat.implementation.MouseEars;
import com.minehut.cosmetics.cosmetics.groups.hat.implementation.MaidHeadband;
import com.minehut.cosmetics.cosmetics.groups.hat.implementation.ExplorerHat;

import java.util.function.Supplier;

public enum Hat implements CosmeticSupplier<HatCosmetic> {
    // explorer
    EXPLORER(ExplorerHat::new),
    // halloween
    WITCH(WitchHat::new),
    CAT_EARS(CatEars::new),
    DEVIL_HORNS(DevilHorns::new),
    FOX_EARS(FoxEars::new),
    MOUSE_EARS(MouseEars::new),
    MAID(MaidHeadband::new),
    FALL_22(Fall22Hat::new),
    ;

    private final Supplier<HatCosmetic> supplier;

    /**
     * @param supplier supplier for cosmetic
     */
    Hat(final Supplier<HatCosmetic> supplier) {
        this.supplier = supplier;
    }

    @Override
    public HatCosmetic get() {
        return supplier.get();
    }
}
