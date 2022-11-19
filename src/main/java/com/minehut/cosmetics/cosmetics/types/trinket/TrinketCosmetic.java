package com.minehut.cosmetics.cosmetics.types.trinket;

import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.properties.Equippable;
import net.kyori.adventure.text.Component;

public abstract class TrinketCosmetic extends Cosmetic implements Equippable {
    protected TrinketCosmetic(final String id, final Component name) {
        super(id, CosmeticCategory.TRINKET, name);
    }
}
