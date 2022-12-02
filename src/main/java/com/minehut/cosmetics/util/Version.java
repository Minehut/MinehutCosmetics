package com.minehut.cosmetics.util;

import org.bukkit.Material;

import java.util.function.Supplier;

public enum Version {
    V_1_19(() -> materialExists("SCULK"));

    private final Supplier<Boolean> supportCheck;

    Version(Supplier<Boolean> supportCheck) {
        this.supportCheck = supportCheck;
    }

    public boolean isSupported() {
        return supportCheck.get();
    }

    private static boolean materialExists(final String name) {
        try {
            Material.valueOf(name);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}
