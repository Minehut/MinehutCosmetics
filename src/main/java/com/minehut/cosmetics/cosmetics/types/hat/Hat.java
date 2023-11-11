package com.minehut.cosmetics.cosmetics.types.hat;


import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.collections.autumn2022.Fall22Hat;
import com.minehut.cosmetics.cosmetics.collections.beta.ExplorerHat;
import com.minehut.cosmetics.cosmetics.collections.dragoncrate.DragonHat;
import com.minehut.cosmetics.cosmetics.collections.dragoncrate.GamerHeadset;
import com.minehut.cosmetics.cosmetics.collections.dragoncrate.MechVisor;
import com.minehut.cosmetics.cosmetics.collections.dragoncrate.SteampunkHat;
import com.minehut.cosmetics.cosmetics.collections.dragoncrate.TurtleHat;
import com.minehut.cosmetics.cosmetics.collections.feb2023.RiceHat;
import com.minehut.cosmetics.cosmetics.collections.halloween2022.CatEars;
import com.minehut.cosmetics.cosmetics.collections.halloween2022.DevilHorns;
import com.minehut.cosmetics.cosmetics.collections.halloween2022.FoxEars;
import com.minehut.cosmetics.cosmetics.collections.halloween2022.MouseEars;
import com.minehut.cosmetics.cosmetics.collections.halloween2022.WitchHat;
import com.minehut.cosmetics.cosmetics.collections.jan2023.NinjaMask;
import com.minehut.cosmetics.cosmetics.collections.maid.MaidHeadband;
import com.minehut.cosmetics.cosmetics.collections.nickelodeon.ArrghHelmet;
import com.minehut.cosmetics.cosmetics.collections.nickelodeon.BandanaDonatello;
import com.minehut.cosmetics.cosmetics.collections.nickelodeon.BandanaLeonardo;
import com.minehut.cosmetics.cosmetics.collections.nickelodeon.BandanaMichelangelo;
import com.minehut.cosmetics.cosmetics.collections.nickelodeon.BandanaRaphael;
import com.minehut.cosmetics.cosmetics.collections.valentines2023.HeartfeltHeadband;
import com.minehut.cosmetics.cosmetics.collections.valentines2023.HeartfeltSunglasses;

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
    STEAMPUNK(SteampunkHat::new),
    TECHNICAL_VISOR(MechVisor::new),
    NINJA_MASK(NinjaMask::new),
    RICE_HAT(RiceHat::new),
    VALENTINES_SUNGLASSES(HeartfeltSunglasses::new),
    VALENTINE_HEADBAND(HeartfeltHeadband::new),
    ARRGH_HELMET(ArrghHelmet::new),
    BANDANA_DONATELLO(BandanaDonatello::new),
    BANDANA_LEONARDO(BandanaLeonardo::new),
    BANDANA_MICHELANGELO(BandanaMichelangelo::new),
    BANDANA_RAPHAEL(BandanaRaphael::new);
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
