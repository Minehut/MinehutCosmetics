package com.minehut.cosmetics.ui.icon;

import org.bukkit.inventory.ItemStack;

/**
 * This interface defines a type which can render itself in a menu.
 */
@FunctionalInterface
public interface SelfRenderableItem {

    /**
     * The function used to render the object.
     *
     * @return The rendered {@link ItemStack}.
     */
    ItemStack render();

    /**
     * Allows a static variation of a {@link SelfRenderableItem} where the product is static.
     *
     * @param itemStack The {@link ItemStack} used for rendering.
     * @return The {@link SelfRenderableItem} generated to return the given {@link ItemStack}.
     */
    static SelfRenderableItem of(ItemStack itemStack) {
        return () -> itemStack;
    }
}
