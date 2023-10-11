package com.minehut.cosmetics.util;

import org.bukkit.Material;

public enum Version {
    V_1_19(materialExists("SCULK")),
    V_1_20(materialExists("BAMBOO_BLOCK"));
    ;

    private final boolean supported;

    Version(boolean supported) {
        this.supported = supported;
    }

    public boolean isSupported() {
        return supported;
    }

    /**
     * Check if the given material is registered
     * this is useful for checking versions since versions before
     * the one the material was added will error and
     * new versions will pass.
     *
     * @param name of the material to check for
     * @return whether that material exists.
     */
    private static boolean materialExists(final String name) {
        try {
            Material.valueOf(name);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}
