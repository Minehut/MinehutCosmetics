package com.minehut.cosmetics.cosmetics.groups.trinket;

import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.properties.Equippable;
import net.kyori.adventure.text.Component;

public abstract class TrinketCosmetic extends Cosmetic implements Equippable {
    protected TrinketCosmetic(final String id,
                              final Component name,
                              final Permission permission,
                              final Permission visibility) {
        super(id, CosmeticCategory.TRINKET, name, permission, visibility);
    }
}
