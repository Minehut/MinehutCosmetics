package com.minehut.cosmetics.crates;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.Permission;
import net.kyori.adventure.text.Component;

import java.util.UUID;

public abstract class Crate extends Cosmetic {

    private final WeightedTable<CosmeticSupplier<? extends Cosmetic>> table;

    protected Crate(String id, CosmeticCategory type, Component name, WeightedTable<CosmeticSupplier<? extends Cosmetic>> table) {
        super(id, type, name);
        this.table = table;
    }

    @Override
    public Permission permission() {
        return Permission.none();
    }

    @Override
    public Permission visibility() {
        return Permission.none();
    }

    public void open(UUID uuid, int amount) {
        Cosmetics.get().api().consumeCosmetic(uuid, category(), id(), amount);
    }
}