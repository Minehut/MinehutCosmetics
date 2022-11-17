package com.minehut.cosmetics.ui;

import com.minehut.cosmetics.ui.icon.ActionHandler;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public abstract class Menu implements Listener {

    private final Plugin plugin;
    private ProxyInventory proxyInventory;
    private Inventory inventory;
    private final Component title;
    private Map<Integer, ActionHandler> actionHandlers;
    private BukkitTask renderTask;
    private Player player;

    public Menu(Plugin plugin, int rows, Component title) {
        Validate.isTrue(rows <= 6 && rows > 0, "The amount of rows in a menu must be between 1 and 6.");
        this.plugin = plugin;
        this.title = title;
        setRows(rows);
        Bukkit.getPluginManager().registerEvents(this, this.plugin);
    }

    public Menu(Plugin plugin, int rows, String title) {
        this(plugin, rows, Component.text(title));
    }

    /**
     * Cleans up the menu. Stops the render task if there is one and unregisters the registered events.
     */
    public void cleanup() {
        if (this.renderTask != null) {
            this.renderTask.cancel();
        }
        HandlerList.unregisterAll(this);
    }

    public void setTitle(Component component) {

    }

    /**
     * Opens the {@link #inventory} to the given {@link Player}.
     *
     * @param player The player to open the {@link #inventory} to.
     */
    public void openTo(Player player) {
        this.player = player;
        renderAsync();
        player.openInventory(this.inventory);
    }

    /**
     * Access the field {@link #player}.
     *
     * @return The value of {@link #player}.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets up a task to automatically re-render the menu. The render task only triggers when there is a viewer.
     *
     * @param delay    The initial delay for rendering.
     * @param interval The interval at which the render happens.
     */
    public void setupRenderTask(long delay, long interval) {
        this.renderTask = Bukkit.getScheduler().runTaskTimerAsynchronously(this.plugin, () -> {
            if (this.player != null) {
                reloadRender();
            }
        }, delay, interval);
    }

    /**
     * Sets the {@link #inventory}'s contents.
     *
     * @param stacks The contents to place into the {@link Inventory}.
     * @see Inventory#setContents(ItemStack[])
     */
    protected void setContents(ItemStack[] stacks) {
        this.inventory.setContents(stacks);
    }

    /**
     * Setter for {@link #actionHandlers}.
     *
     * @param actionHandlers The value to place into {@link #actionHandlers}.
     */
    protected void setActionHandlers(Map<Integer, ActionHandler> actionHandlers) {
        this.actionHandlers = actionHandlers;
    }

    /**
     * Renders the menu asynchronously.
     *
     * @see #reloadRender()
     */
    public void renderAsync() {
        if (this.player != null) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, this::reloadRender);
        }
    }

    /**
     * Reloads the render of the internal {@link ProxyInventory} and applies it.
     *
     * @see #render()
     * @see ProxyInventory#applyToSync(Plugin, Menu)
     */
    private synchronized void reloadRender() {
        this.render();
        this.proxyInventory.applyToSync(plugin, this);
    }

    /**
     * Renders the view into the {@link #proxyInventory}. This should only be run async.
     */
    public abstract void render();

    /**
     * Access the field {@link #proxyInventory}.
     *
     * @return The value of {@link #proxyInventory}.
     */
    public ProxyInventory getProxy() {
        return proxyInventory;
    }

    /**
     * Set the proxy inventory to a new one
     *
     * @param proxy to set to
     */
    public void setProxy(ProxyInventory proxy) {
        this.proxyInventory = proxy;
    }

    public void setRows(int rows) {
        this.proxyInventory = new ProxyInventory(rows);
        this.inventory = Bukkit.createInventory(null, rows * 9, title);
        this.actionHandlers = new HashMap<>(rows * 9);
    }

    /**
     * Distributes the given {@link InventoryClickEvent} to the {@link #actionHandlers} if one exists for the clicked slot.
     *
     * @param event The event to distribute.
     */
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView().getTopInventory() == inventory) {
            event.setResult(Result.DENY);
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            if (event.getRawSlot() < inventory.getSize() && event.getRawSlot() >= 0) {
                ActionHandler handler = actionHandlers.getOrDefault(event.getRawSlot(), null);
                if (handler != null) {
                    handler.clickEvent(player, event.getClick());
                }
            }
        }
    }

    /**
     * Cancels the {@link InventoryDragEvent} given that the {@link Inventory} open is equal to {@link #inventory}.
     *
     * @param event The {@link InventoryDragEvent} to monitor.
     */
    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (event.getView().getTopInventory() == inventory) {
            // we just disable drag by default, there's very few reasons we actually want this
            event.setCancelled(true);
        }
    }

    /**
     * Handles the cleanup and closing of the internal {@link Inventory} given that it is equal to {@link #inventory}.
     *
     * @param event The {@link InventoryCloseEvent} to monitor.
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onClose(InventoryCloseEvent event) {
        if (event.getView().getTopInventory() == inventory) {
            cleanup();
            this.inventory.getViewers().forEach(e -> {
                if (!e.getUniqueId().equals(event.getPlayer().getUniqueId())) {
                    e.closeInventory();
                }
            });
        }
    }
}
