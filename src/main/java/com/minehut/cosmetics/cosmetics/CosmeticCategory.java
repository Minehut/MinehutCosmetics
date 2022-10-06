package com.minehut.cosmetics.cosmetics;

import com.minehut.cosmetics.cosmetics.groups.balloon.Balloon;
import com.minehut.cosmetics.cosmetics.groups.companion.Companion;
import com.minehut.cosmetics.cosmetics.groups.hat.Hat;
import com.minehut.cosmetics.cosmetics.groups.item.Item;
import com.minehut.cosmetics.cosmetics.groups.particle.Particle;
import com.minehut.cosmetics.cosmetics.groups.wing.Wing;
import com.minehut.cosmetics.util.EnumUtil;

import java.util.Optional;
import java.util.function.Function;

public enum CosmeticCategory {
    PARTICLE("Particles", query -> EnumUtil.valueOfSafe(Particle.class, query).map(Particle::get)),
    HAT("Hats", query -> EnumUtil.valueOfSafe(Hat.class, query).map(Hat::get)),
    COMPANION("Companions", query -> EnumUtil.valueOfSafe(Companion.class, query).map(Companion::get)),
    ITEM("Items", query -> EnumUtil.valueOfSafe(Item.class, query).map(Item::get)),
    BALLOON("Balloons", query -> EnumUtil.valueOfSafe(Balloon.class, query).map(Balloon::get)),
    WING("Wings", query -> EnumUtil.valueOfSafe(Wing.class, query).map(Wing::get));

    private final String categoryName;
    private final Function<String, Optional<Cosmetic>> getCosmetic;

    CosmeticCategory(String categoryName, Function<String, Optional<Cosmetic>> getCosmetic) {
        this.categoryName = categoryName;
        this.getCosmetic = getCosmetic;

    }

    public String getCategoryName() {
        return categoryName;
    }

    public Optional<Cosmetic> getCosmetic(String query) {
        return getCosmetic.apply(query);
    }

    /**
     * Get a cosmetic using a category string and id string,
     * this is primarily useful for grabbing cosmetic values in game
     * from the "equipped" field of the cosmetic profile
     *
     * @param category of the cosmetic
     * @param id       of the cosmetic
     * @return a possible cosmetic for the given category and id
     */
    public static Optional<Cosmetic> getCosmetic(String category, String id) {
        return EnumUtil
                .valueOfSafe(CosmeticCategory.class, category.toUpperCase())
                .flatMap((cat) -> cat.getCosmetic(id.toUpperCase()));
    }
}
