package com.minehut.cosmetics.cosmetics.types.hat;


import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.collections.betacrate.DragonHat;
import com.minehut.cosmetics.cosmetics.collections.betacrate.GamerHeadset;
import com.minehut.cosmetics.cosmetics.collections.betacrate.SteampunkHat;
import com.minehut.cosmetics.cosmetics.collections.betacrate.TurtleHat;
import com.minehut.cosmetics.cosmetics.collections.halloween2022.CatEars;
import com.minehut.cosmetics.cosmetics.collections.halloween2022.DevilHorns;
import com.minehut.cosmetics.cosmetics.collections.autumn2022.Fall22Hat;
import com.minehut.cosmetics.cosmetics.collections.halloween2022.FoxEars;
import com.minehut.cosmetics.cosmetics.collections.halloween2022.WitchHat;
import com.minehut.cosmetics.cosmetics.collections.halloween2022.MouseEars;
import com.minehut.cosmetics.cosmetics.collections.maid.MaidHeadband;
import com.minehut.cosmetics.cosmetics.collections.beta.ExplorerHat;

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
    GAMER_HEADSET(GamerHeadset::new),
    DRAGON(DragonHat::new),
    TURTLE(TurtleHat::new),
    STEAMPUNK(SteampunkHat::new);

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
