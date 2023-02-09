package com.minehut.cosmetics.cosmetics.crates.impl;

import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.Rarity;
import com.minehut.cosmetics.util.structures.Pair;

import java.util.List;

public class CrateEntry {

    private final Rarity rarity;
    private final List<Pair<CosmeticSupplier<? extends Cosmetic>, Integer>> items;

    public CrateEntry(Rarity rarity, List<Pair<CosmeticSupplier<? extends Cosmetic>, Integer>> items) {
        this.rarity = rarity;
        this.items = items;
    }

    public static CrateEntry of(Rarity rarity, List<Pair<CosmeticSupplier<? extends Cosmetic>, Integer>> items) {
        return new CrateEntry(rarity, items);
    }

    public Rarity rarity() {
        return rarity;
    }

    public List<Pair<CosmeticSupplier<? extends Cosmetic>, Integer>> items() {
        return items;
    }
}
