package com.minehut.cosmetics.ui.icon;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * A MenuItem references the combination of a {@link SelfRenderableItem} and an {@link ActionHandler}.
 *
 * @see SelfRenderableItem
 * @see ActionHandler
 */
public class MenuItem implements SelfRenderableItem, ActionHandler {

    /**
     * The {@link SelfRenderableItem} used as a "display" for the Menu.
     */
    private final SelfRenderableItem itemStackSupplier;

    /**
     * The internal {@link ActionHandler} used as a click event handler for the menu.
     *
     * @see ActionHandler
     */
    private final ActionHandler internalActionHandler;

    /**
     * @deprecated Should not use the constructor, use {@link MenuItem#of(SelfRenderableItem, ActionHandler)}
     */
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    public MenuItem(@NotNull SelfRenderableItem stackSupplier, @NotNull ActionHandler internalActionHandler) {
        this.itemStackSupplier = stackSupplier;
        this.internalActionHandler = internalActionHandler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clickEvent(Player player, ClickType type) {
        internalActionHandler.clickEvent(player, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemStack render() {
        return itemStackSupplier.render();
    }

    /**
     * Creates a new {@link MenuItem} with the specified {@link ItemStack} as the display render.
     *
     * @param itemStack The {@link ItemStack} to use as the render.
     * @return The created {@link MenuItem}.
     */
    public static MenuItem of(ItemStack itemStack) {
        return MenuItem.of(SelfRenderableItem.of(itemStack));
    }

    /**
     * Creates a new {@link MenuItem} with the specified {@link ItemStack} as the display render and the specified {@link
     * ActionHandler} as the event handler.
     *
     * @param itemStack     The {@link ItemStack} to use as the render.
     * @param actionHandler The {@link ActionHandler} to consume events.
     * @return The created {@link MenuItem}.
     */
    public static MenuItem of(ItemStack itemStack, ActionHandler actionHandler) {
        return MenuItem.of(SelfRenderableItem.of(itemStack), actionHandler);
    }

    /**
     * Creates a new {@link MenuItem} with the specified {@link SelfRenderableItem} as the display render.
     *
     * @param item The {@link SelfRenderableItem} to use as the render.
     * @return The created {@link MenuItem}.
     */
    public static MenuItem of(SelfRenderableItem item) {
        return MenuItem.of(item, ActionHandler.doNothing());
    }

    /**
     * Creates a new {@link MenuItem} with the specified {@link SelfRenderableItem} as the display render and the specified
     * {@link ActionHandler} as the event handler.
     *
     * @param itemStack     The {@link SelfRenderableItem} to use as the render.
     * @param actionHandler The {@link ActionHandler} to consume events.
     * @return The created {@link MenuItem}.
     */
    public static MenuItem of(SelfRenderableItem itemStack, ActionHandler actionHandler) {
        return new MenuItem(itemStack, actionHandler);
    }
}
