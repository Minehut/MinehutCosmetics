package com.minehut.cosmetics.cosmetics;

public interface CosmeticSupplier<T extends Cosmetic> {
    T get();
    boolean isVisible();
}