package com.minehut.cosmetics.cosmetics.properties;

import org.bukkit.inventory.ItemStack;

public interface Skinnable {
    void applySkin(ItemStack item);

    default void removeSkin(ItemStack item) {
        item.editMeta(meta -> meta.setCustomModelData(0));
    }
}
