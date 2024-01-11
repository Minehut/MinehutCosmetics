package com.minehut.cosmetics.cosmetics;

import com.minehut.cosmetics.cosmetics.crates.CrateType;
import com.minehut.cosmetics.cosmetics.types.balloon.Balloon;
import com.minehut.cosmetics.cosmetics.types.companion.Companion;
import com.minehut.cosmetics.cosmetics.types.emoji.Emoji;
import com.minehut.cosmetics.cosmetics.types.hat.Hat;
import com.minehut.cosmetics.cosmetics.types.item.Item;
import com.minehut.cosmetics.cosmetics.types.particle.Particle;
import com.minehut.cosmetics.cosmetics.types.trinket.Trinket;
import com.minehut.cosmetics.cosmetics.types.wing.Wing;
import com.minehut.cosmetics.util.EnumUtil;

import java.util.Optional;
import java.util.function.Function;

public enum CosmeticCategory {
    PARTICLE("Particles", query -> EnumUtil.valueOfSafe(Particle.class, query).map(Particle::get), true),
    HAT("Hats", query -> EnumUtil.valueOfSafe(Hat.class, query).map(Hat::get), false),
    COMPANION("Companions", query -> EnumUtil.valueOfSafe(Companion.class, query).map(Companion::get), true),
    ITEM("Items", query -> EnumUtil.valueOfSafe(Item.class, query).map(Item::get), false),
    BALLOON("Balloons", query -> EnumUtil.valueOfSafe(Balloon.class, query).map(Balloon::get), true),
    WING("Backpieces", query -> EnumUtil.valueOfSafe(Wing.class, query).map(Wing::get), true),
    TRINKET("Trinkets", query -> EnumUtil.valueOfSafe(Trinket.class, query).map(Trinket::get), false),
    EMOJI("Emojis", query -> EnumUtil.valueOfSafe(Emoji.class, query).map(Emoji::get), false),
    CRATE("Crates", query -> EnumUtil.valueOfSafe(CrateType.class, query).map(CrateType::get), false);

    private final String categoryName;
    private final Function<String, Optional<Cosmetic>> getCosmetic;
    private final boolean saveLocal;

    /**
     * @param categoryName name for this slot
     * @param getCosmetic  method to grab a cosmetic from a given query
     * @param saveLocal    whether to save local equip for this slot
     */
    CosmeticCategory(String categoryName, Function<String, Optional<Cosmetic>> getCosmetic, boolean saveLocal) {
        this.categoryName = categoryName;
        this.getCosmetic = getCosmetic;
        this.saveLocal = saveLocal;
    }

    public String categoryName() {
        return categoryName;
    }

    public Optional<Cosmetic> cosmetic(String query) {
        return getCosmetic.apply(query);
    }

    public boolean isSaveLocal() {
        return saveLocal;
    }
}
