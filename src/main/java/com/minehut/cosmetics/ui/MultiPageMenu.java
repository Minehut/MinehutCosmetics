package com.minehut.cosmetics.ui;

import com.minehut.cosmetics.ui.icon.MenuItem;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * This class represents a multi-paged menu which has a list of items to be displayed within the menu.
 *
 * @param <T> The type of items which will be put into the menu.
 */
public abstract class MultiPageMenu<T> extends Menu {

    private final AtomicInteger page;
    private final AtomicInteger itemListSizeCache;
    private final Predicate<T> itemFilter;
    private MenuItem previousPageItem;
    private int previousPageItemSlot;
    private MenuItem pageView;
    private int pageViewItemSlot;
    private MenuItem nextPageItem;
    private int nextPageItemSlot;

    public MultiPageMenu(Plugin plugin, int rows, Component title) {
        this(plugin, rows, title, t -> true);
    }

    public MultiPageMenu(Plugin plugin, int rows, Component title, Predicate<T> itemFilter) {
        super(plugin, rows, title);
        this.page = new AtomicInteger(0);
        this.itemListSizeCache = new AtomicInteger(0);

        // default previous page item
        this.previousPageItem = MenuItem.of(
                () -> ItemBuilder.of(Material.ARROW)
                        .display(Component.text("Previous Page").color(NamedTextColor.RED))
                        .build(),
                (player, click) -> this.decrementPage()
        );
        this.previousPageItemSlot = ((rows - 1) * 9) + 3;

        // page view
        this.pageView = MenuItem.of(() -> ItemBuilder.of(Material.PAPER)
                .glow(true)
                .display(Component.text("Page " + (page.get() + 1)).color(NamedTextColor.BLUE))
                .build()
        );
        this.pageViewItemSlot = ((rows - 1) * 9) + 4;

        // default next page
        this.nextPageItem = MenuItem.of(
                () -> ItemBuilder.of(Material.ARROW)
                        .display(Component.text("Next Page").color(NamedTextColor.RED))
                        .build(),
                (player, click) -> this.incrementPage()
        );
        this.nextPageItemSlot = ((rows - 1) * 9) + 5;
        this.itemFilter = itemFilter;
    }

    /**
     * Accesses the field {@link #page}.
     *
     * @return The value of {@link #page}.
     */
    public AtomicInteger getPage() {
        return page;
    }

    /**
     * @return A {@link List} of items of type {@link T}.
     */
    public abstract List<T> getItemList();

    /**
     * @return The amount of items which can exist per page.
     */
    public abstract int getItemsPerPage();

    /**
     * @return A {@link Set} of slots which the page items cannot be placed.
     */
    public abstract Set<Integer> getRestrictedSlots();

    /**
     * Adds an page item of type {@link T} to the menu.
     *
     * @param item The item to add.
     * @param slot The slot to add the item to.
     */
    public abstract void addItem(T item, int slot);

    /**
     * @return The max page available for the menu.
     */
    public int getMaxPage() {
        return Math.max(0, (int) Math.ceil((double) itemListSizeCache.get() / (double) getItemsPerPage()) - 1);
    }

    /**
     * Increments the value of {@link #page} and reloads the render.
     *
     * @return Whether or not the page has changed.
     */
    public boolean incrementPage() {
        int newPage = page.get() + 1;

        if (newPage > getMaxPage()) {
            return false;
        }

        this.page.incrementAndGet();
        renderAsync();
        return true;
    }

    /**
     * Decrements the value of {@link #page} and reloads the render.
     *
     * @return Whether or not the page has changed.
     */
    public boolean decrementPage() {
        if (page.get() == 0) {
            return false;
        }
        page.decrementAndGet();
        renderAsync();
        return true;
    }

    /**
     * Renders the current page of items based on the {@link #getItemList()}. With the current item list it updates the
     * {@link #itemListSizeCache}.
     */
    public void renderPage() {
        List<T> itemList = getItemList();

        int listSize = itemList.size();
        this.itemListSizeCache.set(listSize);

        int maxPage = getMaxPage();

        if (page.get() > maxPage) {
            page.set(maxPage);
        }

        int start = getItemsPerPage() * page.get();

        if (start > itemList.size()) {
            iterateSlots().forEachRemaining(i -> getProxy().setItem(i, (ItemStack) null));
        } else {
            int end = Math.min(start + getItemsPerPage(), itemList.size());
            Iterator<Integer> slotIterator = iterateSlots();
            Iterator<T> itemIterator = itemList.stream().filter(itemFilter).skip(start).limit(end - start).iterator();
            while (itemIterator.hasNext()) {
                addItem(itemIterator.next(), slotIterator.next());
            }
            slotIterator.forEachRemaining(slot -> getProxy().setItem(slot, (ItemStack) null));
        }
    }

    /**
     * Creates an {@link Iterator} for the slots used for the page items.
     *
     * @return The created {@link Iterator} or slots.
     */
    private Iterator<Integer> iterateSlots() {
        List<Integer> slotSet = new ArrayList<>();
        for (int i = 0, slot = 0; i < getItemsPerPage() && slot < getProxy().getSize(); i++, slot++) {
            if (getRestrictedSlots().contains(slot)) {
                i--;
            } else {
                slotSet.add(slot);
            }
        }
        return slotSet.iterator();
    }

    /**
     * Automatically clears out the inventory, renders the page, and adds in the default view items.
     *
     * @see #renderPage()
     * @see MenuItem#render()
     */
    @Override
    public void render() {
        getProxy().clearItems();
        getProxy().clearActionHandlers();

        this.renderPage();

        int currentPage = page.get();
        if (currentPage > 0) {
            getProxy().setItem(getPreviousPageItemSlot(), getPreviousPageItem());
        } else {
            getProxy().setItem(getPreviousPageItemSlot(), (ItemStack) null);
            getProxy().setActionHandler(getPreviousPageItemSlot(), null);
        }
        getProxy().setItem(getPageViewItemSlot(), getPageView());
        if (currentPage < getMaxPage()) {
            getProxy().setItem(getNextPageItemSlot(), getNextPageItem());
        } else {
            getProxy().setItem(getNextPageItemSlot(), (ItemStack) null);
            getProxy().setActionHandler(getNextPageItemSlot(), null);
        }
    }

    /**
     * Access the field {@link #nextPageItem}.
     *
     * @return The value of {@link #nextPageItem}.
     */
    public MenuItem getNextPageItem() {
        return nextPageItem;
    }

    /**
     * Setter for field {@link #nextPageItem}.
     *
     * @param nextPageItem The value to place into {@link #nextPageItem}.
     */
    public void setNextPageItem(MenuItem nextPageItem) {
        this.nextPageItem = nextPageItem;
    }

    /**
     * Access the field {@link #nextPageItemSlot}.
     *
     * @return The value of {@link #nextPageItemSlot}.
     */
    public int getNextPageItemSlot() {
        return nextPageItemSlot;
    }

    /**
     * Setter for field {@link #nextPageItemSlot}.
     *
     * @param nextPageItemSlot The value to place into {@link #nextPageItemSlot}.
     */
    public void setNextPageItemSlot(int nextPageItemSlot) {
        this.nextPageItemSlot = nextPageItemSlot;
    }

    /**
     * Access the field {@link #previousPageItem}.
     *
     * @return The value of {@link #previousPageItem}.
     */
    public MenuItem getPreviousPageItem() {
        return previousPageItem;
    }

    /**
     * Setter for field {@link #previousPageItem}.
     *
     * @param previousPageItem The value to place into {@link #previousPageItem}.
     */
    public void setPreviousPageItem(MenuItem previousPageItem) {
        this.previousPageItem = previousPageItem;
    }

    /**
     * Access the field {@link #previousPageItemSlot}.
     *
     * @return The value of {@link #previousPageItemSlot}.
     */
    public int getPreviousPageItemSlot() {
        return previousPageItemSlot;
    }

    /**
     * Setter for field {@link #previousPageItemSlot}.
     *
     * @param previousPageItemSlot The value to place into {@link #previousPageItemSlot}.
     */
    public void setPreviousPageItemSlot(int previousPageItemSlot) {
        this.previousPageItemSlot = previousPageItemSlot;
    }

    /**
     * Access the field {@link #pageView}.
     *
     * @return The value of {@link #pageView}.
     */
    public MenuItem getPageView() {
        return pageView;
    }

    /**
     * Setter for field {@link #pageView}.
     *
     * @param pageView The value to place into {@link #pageView}.
     */
    public void setPageView(MenuItem pageView) {
        this.pageView = pageView;
    }

    /**
     * Access the field {@link #pageViewItemSlot}.
     *
     * @return The value of {@link #pageViewItemSlot}.
     */
    public int getPageViewItemSlot() {
        return pageViewItemSlot;
    }

    /**
     * Setter for field {@link #pageViewItemSlot}.
     *
     * @param pageViewItemSlot The value to place into {@link #pageViewItemSlot}.
     */
    public void setPageViewItemSlot(int pageViewItemSlot) {
        this.pageViewItemSlot = pageViewItemSlot;
    }
}
