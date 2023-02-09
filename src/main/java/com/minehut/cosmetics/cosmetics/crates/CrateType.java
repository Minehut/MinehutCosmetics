package com.minehut.cosmetics.cosmetics.crates;

import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.crates.impl.DragonCrate;
import com.minehut.cosmetics.cosmetics.crates.impl.HeartfeltCrate;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum CrateType implements CosmeticSupplier<Crate> {
    DRAGON_CRATE(DragonCrate::new),
    HEARTFELT(HeartfeltCrate::new);

    private final Supplier<Crate> supplier;

    CrateType(Supplier<Crate> supplier) {
        this.supplier = supplier;
    }

    @Override
    public @NotNull Crate get() {
        return supplier.get();
    }
}