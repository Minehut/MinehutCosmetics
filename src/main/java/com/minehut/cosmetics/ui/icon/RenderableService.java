package com.minehut.cosmetics.ui.icon;

import org.bukkit.inventory.ItemStack;

/**
 * This is a renderable service which translates an object of type {@link T} to an {@link ItemStack} for rendering.
 *
 * @param <T> The type of object transformed into an {@link ItemStack}.
 */
@FunctionalInterface
public interface RenderableService<T> {

    /**
     * A function which translates an item of type {@link T} into an {@link ItemStack}.
     *
     * @param item The object to be translated.
     * @return The translation result.
     */
    ItemStack render(T item);
}
