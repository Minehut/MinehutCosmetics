package com.minehut.cosmetics.cosmetics.types.trinket;


import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.collections.feb2023.RoyalScepter;
import com.minehut.cosmetics.cosmetics.collections.netflix2022.SpooktacularBoombox;
import com.minehut.cosmetics.cosmetics.collections.nickelodeon.AARGHVacuumTrinket;
import com.minehut.cosmetics.cosmetics.collections.nickelodeon.Clarinet;
import com.minehut.cosmetics.cosmetics.collections.nickelodeon.MagicConchTrinket;
import com.minehut.cosmetics.cosmetics.collections.nickelodeon.Pizza;
import com.minehut.cosmetics.cosmetics.collections.valentines2023.HeartfeltStaff;
import com.minehut.cosmetics.cosmetics.collections.winter2022.IceScepterTrinket;
import com.minehut.cosmetics.cosmetics.collections.winter2023.IceSpellbookTrinket;

import java.util.function.Supplier;

public enum Trinket implements CosmeticSupplier<TrinketCosmetic> {
    SPOOKTACULAR_BOOMBOX(SpooktacularBoombox::new),
    ICE_SCEPTER(IceScepterTrinket::new),
    ICE_SPELLBOOK(IceSpellbookTrinket::new),
    ROYAL_SCEPTER(RoyalScepter::new),
    VALENTINE_STAFF(HeartfeltStaff::new),
    // nickelodeon
    AARGH_VACCUUM(AARGHVacuumTrinket::new),
    CLARINET(Clarinet::new),
    PIZZA(Pizza::new),
    MAGIC_CONCH(MagicConchTrinket::new);

    private final Supplier<TrinketCosmetic> supplier;

    Trinket(final Supplier<TrinketCosmetic> supplier) {
        this.supplier = supplier;
    }

    @Override
    public TrinketCosmetic get() {
        return supplier.get();
    }
}
