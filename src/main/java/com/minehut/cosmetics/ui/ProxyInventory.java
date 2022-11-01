package com.minehut.cosmetics.ui;

import com.minehut.cosmetics.ui.icon.ActionHandler;
import com.minehut.cosmetics.ui.icon.MenuItem;
import com.minehut.cosmetics.ui.icon.RenderableService;
import com.minehut.cosmetics.ui.icon.SelfRenderableItem;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

/**
 * A thread-safe representation of the contents of a {@link Menu}.
 */
public class ProxyInventory {

    /**
     * A {@link HashMap} connecting the item slots and {@link ActionHandler}s associated with them.
     */
    private final Map<Integer, ActionHandler> actionHandlers;

    /**
     * The absolute size of the menu.
     */
    private final int size;

    /**
     * The {@link ItemStack} contents of the inventory. The size is guaranteed to be {@link #size}.
     */
    private ItemStack[] itemStacks;

    /**
     * Constructs a proxy inventory with the size being the given rows multiplied by nine. The rows must be between 1 and 6
     * inclusive.
     *
     * @param rows The amount of rows.
     * @throws IllegalArgumentException When the rows is less than one or greater than six.
     */
    public ProxyInventory(int rows) {
        Validate.isTrue(rows <= 6 && rows > 0, "The amount of rows in a menu must be between 1 and 6.");
        this.size = rows * 9;
        this.itemStacks = new ItemStack[this.size];
        this.actionHandlers = new HashMap<>(this.size);
    }

    /**
     * Sets the contents of the menu including the {@link ActionHandler}s and {@link ItemStack} contents.
     *
     * @param menuItems The {@link MenuItem}s to spread into the inventory.
     */
    public void setContents(MenuItem[] menuItems) {
        clearActionHandlers();

        ItemStack[] itemStacks = new ItemStack[getSize()];

        for (int i = 0; i < itemStacks.length && i < menuItems.length; i++) {
            itemStacks[i] = menuItems[i].render();
            setActionHandler(i, menuItems[i]);
        }

        setContents(itemStacks);
    }

    /**
     * Renders and spreads the result into the contents via {@link #setContents(ItemStack[])}.
     *
     * @param renderableItems The {@link SelfRenderableItem}s to render into the menu.
     * @see #setContents(ItemStack[])
     */
    public void setContents(SelfRenderableItem[] renderableItems) {
        ItemStack[] itemStacks = new ItemStack[getSize()];

        for (int i = 0; i < itemStacks.length && i < renderableItems.length; i++) {
            itemStacks[i] = renderableItems[i].render();
        }

        setContents(itemStacks);
    }

    /**
     * Renders and spreads the result into the contents.
     *
     * @param renderableServices The {@link RenderableService} used to render the given items.
     * @param items              An array of {@link T} items to be rendered by the given {@link RenderableService}.
     * @param <T>                The type defining the {@link RenderableService}.
     * @see #setContents(ItemStack[])
     */
    public <T> void setContents(RenderableService<T> renderableServices, T[] items) {
        ItemStack[] itemStacks = new ItemStack[getSize()];

        for (int i = 0; i < itemStacks.length && i < items.length; i++) {
            itemStacks[i] = renderableServices.render(items[i]);
        }

        setContents(itemStacks);
    }

    /**
     * Setter for field {@link #itemStacks}.
     *
     * @param itemStacks The value to place into {@link #itemStacks}.
     */
    public void setContents(ItemStack[] itemStacks) {
        this.itemStacks = itemStacks;
    }

    /**
     * Accesses the field {@link #itemStacks}.
     *
     * @return The value of the field {@link #itemStacks}.
     */
    public ItemStack[] getContents() {
        return this.itemStacks;
    }

    /**
     * Combines an {@link ItemStack} and an {@link ActionHandler} into a {@link MenuItem} to put into the menu.
     *
     * @param slot          The slot to place the created {@link MenuItem}.
     * @param itemStack     The {@link ItemStack} to use for the {@link MenuItem}.
     * @param actionHandler The {@link ActionHandler} to use for the {@link MenuItem}.
     * @see #setItem(int, MenuItem)
     */
    public void setItem(int slot, ItemStack itemStack, ActionHandler actionHandler) {
        setItem(slot, MenuItem.of(itemStack, actionHandler));
    }

    /**
     * Uses a {@link MenuItem} in a specified slot for the menu.
     *
     * @param slot     The slot to apply the {@link MenuItem} to.
     * @param menuItem The {@link MenuItem} to put into the inventory.
     * @throws IllegalArgumentException When the given slot is outside the range of {@code 0 -> (size - 1)}.
     * @see #setItem(int, ItemStack)
     * @see #setActionHandler(int, ActionHandler)
     */
    public void setItem(int slot, MenuItem menuItem) {
        Validate.isTrue(slot >= 0 && slot < getSize(), "The given slot must be within the size of the inventory.");
        setItem(slot, menuItem.render());
        setActionHandler(slot, menuItem);
    }

    /**
     * Renders a {@link SelfRenderableItem} in the given slot for the menu.
     *
     * @param slot           The slot to render the {@link SelfRenderableItem}.
     * @param renderableItem The {@link SelfRenderableItem} to render.
     * @see #setItem(int, ItemStack)
     */
    public void setItem(int slot, SelfRenderableItem renderableItem) {
        setItem(slot, renderableItem.render());
    }

    /**
     * Renders a given item of type {@link T} using the given {@link RenderableService}.
     *
     * @param slot              The slot to render the {@link T} item.
     * @param renderableService The {@link RenderableService} to be used to render the item.
     * @param item              An item of type {@link T} to be rendered by the {@link RenderableService}.
     * @param <T>               The type of the {@link RenderableService}.
     * @see #setItem(int, ItemStack)
     */
    public <T> void setItem(int slot, RenderableService<T> renderableService, T item) {
        setItem(slot, renderableService.render(item));
    }

    /**
     * Sets a specific slot to use a specific {@link ItemStack} as a render.
     *
     * @param slot      The slot to place the {@link ItemStack}.
     * @param itemStack The {@link ItemStack} to use in the slot.
     * @throws IllegalArgumentException When the given slot is outside the range of {@code 0 -> (size - 1)}.
     */
    public void setItem(int slot, ItemStack itemStack) {
        Validate.isTrue(slot >= 0 && slot < getSize(), "The given slot must be within the size of the inventory.");
        this.itemStacks[slot] = itemStack;
    }

    /**
     * Combines an {@link ItemStack} and an {@link ActionHandler} into a {@link MenuItem} to add into the menu.
     *
     * @param itemStack     The {@link ItemStack} to use for the {@link MenuItem}.
     * @param actionHandler The {@link ActionHandler} to use for the {@link MenuItem}.
     * @see #addItem(MenuItem)
     */
    public void addItem(ItemStack itemStack, ActionHandler actionHandler) {
        addItem(MenuItem.of(itemStack, actionHandler));
    }

    /**
     * Adds the given {@link MenuItem} to the menu in the next available slot.
     *
     * @param item The {@link MenuItem} to add to the menu.
     */
    public void addItem(MenuItem item) {
        for (int i = 0; i < size; i++) {
            if (this.itemStacks[i] == null) {
                setItem(i, item.render());
                setActionHandler(i, item);
                break;
            }
        }
    }

    /**
     * Renders a given {@link SelfRenderableItem} and adds it to the menu.
     *
     * @param renderableItem The {@link SelfRenderableItem} to render.
     * @see #addItem(ItemStack)
     */
    public void addItem(SelfRenderableItem renderableItem) {
        addItem(renderableItem.render());
    }

    /**
     * Renders a given item of type {@link T} using a given {@link RenderableService} and adds it to the menu.
     *
     * @param renderableService The {@link RenderableService} to render the item.
     * @param item              The item to render using the given {@link RenderableService}.
     * @param <T>               The type of the given {@link RenderableService}.
     * @see #addItem(ItemStack)
     */
    public <T> void addItem(RenderableService<T> renderableService, T item) {
        addItem(renderableService.render(item));
    }

    /**
     * Adds an {@link ItemStack} into the next available slot of the menu.
     *
     * @param itemStack The {@link ItemStack} to add to the menu.
     */
    public void addItem(ItemStack itemStack) {
        for (int i = 0; i < size; i++) {
            if (this.itemStacks[i] == null) {
                setItem(i, itemStack);
                break;
            }
        }
    }

    /**
     * Clears the {@link #itemStacks} contents.
     */
    public void clearItems() {
        this.itemStacks = new ItemStack[this.size];
    }

    /**
     * Binds an {@link ActionHandler} to a specific slot of the menu.
     *
     * @param slot          The slot to bind the {@link ActionHandler}.
     * @param actionHandler The {@link ActionHandler} to bind.
     * @throws IllegalArgumentException When the given slot is outside the range of {@code 0 -> (size - 1)}.
     */
    public void setActionHandler(int slot, ActionHandler actionHandler) {
        Validate.isTrue(slot >= 0 && slot < getSize(), "The given slot must be within the size of the inventory.");
        this.actionHandlers.put(slot, actionHandler);
    }

    /**
     * Clears the {@link #actionHandlers} contents.
     *
     * @see Map#clear()
     */
    public void clearActionHandlers() {
        this.actionHandlers.clear();
    }

    /**
     * Accesses the field {@link #actionHandlers}.
     *
     * @return The value of {@link #actionHandlers}.
     */
    public Map<Integer, ActionHandler> getActionHandlers() {
        return this.actionHandlers;
    }

    /**
     * Gets the amount of rows inside of the Menu.
     *
     * @return The row count of the menus. Derived from {@code size / 9}.
     */
    public int getRowCount() {
        return size / 9;
    }

    /**
     * Access the field {@link #size}.
     *
     * @return The value of {@link #size}.
     */
    public int getSize() {
        return size;
    }

    /**
     * Applies the proxy inventory contents into a {@link Menu}.
     *
     * @param plugin The {@link Plugin} requesting the operation used to synchronize the operation.
     * @param menu   The {@link Menu} to apply the contents to.
     */
    public void applyToSync(Plugin plugin, Menu menu) {
        ItemStack[] copyStacks = new ItemStack[this.size];
        System.arraycopy(this.itemStacks, 0, copyStacks, 0, this.size);
        Map<Integer, ActionHandler> copyActions = new HashMap<>(actionHandlers);
        Bukkit.getScheduler().runTask(plugin, () -> {
            menu.setContents(copyStacks);
            menu.setActionHandlers(copyActions);
        });
    }
}
