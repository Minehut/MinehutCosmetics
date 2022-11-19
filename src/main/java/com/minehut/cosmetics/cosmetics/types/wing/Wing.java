package com.minehut.cosmetics.cosmetics.types.wing;

import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.collections.betacrate.ArcadeWings;
import com.minehut.cosmetics.cosmetics.collections.betacrate.DragonWings;
import com.minehut.cosmetics.cosmetics.collections.betacrate.LargePegasusWings;
import com.minehut.cosmetics.cosmetics.collections.betacrate.PegasusWings;
import com.minehut.cosmetics.cosmetics.collections.halloween2022.BatWings;
import com.minehut.cosmetics.cosmetics.collections.maid.BlackMaidBow;
import com.minehut.cosmetics.cosmetics.collections.autumn2022.Fall22LeafSwordWings;
import com.minehut.cosmetics.cosmetics.collections.autumn2022.Fall22Wings;
import com.minehut.cosmetics.cosmetics.collections.halloween2022.Halloween22ScytheBack;
import com.minehut.cosmetics.cosmetics.collections.dev.KatanaBack;
import com.minehut.cosmetics.cosmetics.collections.maid.WhiteMaidBow;

import java.util.function.Supplier;

public enum Wing implements CosmeticSupplier<WingCosmetic> {
    KATANA(KatanaBack::new),
    HALLO_22_SCYTHE(Halloween22ScytheBack::new),
    BAT(BatWings::new),
    BOW_WHITE(WhiteMaidBow::new),
    BOW_BLACK(BlackMaidBow::new),
    FALL_22(Fall22Wings::new),
    FALL_22_LEAF_SWORD(Fall22LeafSwordWings::new),
    DRAGON(DragonWings::new),
    PEGASUS(PegasusWings::new),
    PEGASUS_LARGE(LargePegasusWings::new),
    ARCADE(ArcadeWings::new),
    ;

    private final Supplier<WingCosmetic> supplier;

    Wing(final Supplier<WingCosmetic> supplier) {
        this.supplier = supplier;
    }

    @Override
    public WingCosmetic get() {
        return supplier.get();
    }
}
