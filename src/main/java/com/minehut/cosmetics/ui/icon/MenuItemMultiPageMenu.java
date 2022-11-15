package com.minehut.cosmetics.ui.icon;

import com.minehut.cosmetics.ui.MultiPageMenu;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.Plugin;

import java.util.function.Predicate;

/**
 * This class represents a multi-paged menu which has a list of items to be displayed within the menu.
 *
 * @param <T> The type of items which will be put into the menu. Restricted to type {@link MenuItem}.
 */
public abstract class MenuItemMultiPageMenu<T extends MenuItem> extends MultiPageMenu<T> {

    public MenuItemMultiPageMenu(Plugin plugin, int rows, Component title) {
        super(plugin, rows, title);
    }

    public MenuItemMultiPageMenu(Plugin plugin, int rows, Component title, Predicate<T> itemFilter) {
        super(plugin, rows, title, itemFilter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addItem(T item, int slot) {
        getProxy().setItem(slot, item.render());
        getProxy().setActionHandler(slot, item);
    }
}
