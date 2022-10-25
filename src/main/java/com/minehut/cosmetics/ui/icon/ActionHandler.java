package com.minehut.cosmetics.menu.icon;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.function.Consumer;

/**
 * Handles click event actions within a ui.
 */
@FunctionalInterface
public interface ActionHandler {

    /**
     * Function called when a specific item within a ui linked with this action handler is clicked.
     *
     * @param player The {@link Player} who clicked the item.
     * @param type   The {@link ClickType} associated with the click.
     */
    void clickEvent(Player player, ClickType type);

    /**
     * Creates an {@link ActionHandler} which triggers specifically on a right click event in the menu.
     *
     * @param playerConsumer The event handler without the click type present.
     * @return The created {@link ActionHandler}.
     */
    static ActionHandler rightClick(Consumer<Player> playerConsumer) {
        return (p, c) -> {
            if (c.isRightClick()) {
                playerConsumer.accept(p);
            }
        };
    }

    /**
     * Creates an {@link ActionHandler} which triggers specifically on a left click event in the menu.
     *
     * @param playerConsumer The event handler without the click type present.
     * @return The created {@link ActionHandler}.
     */
    static ActionHandler leftClick(Consumer<Player> playerConsumer) {
        return (p, c) -> {
            if (c.isLeftClick()) {
                playerConsumer.accept(p);
            }
        };
    }

    /**
     * Creates an {@link ActionHandler} which does nothing.
     *
     * @return The created {@link ActionHandler}.
     */
    static ActionHandler doNothing() {
        return (_p, _c) -> {
        };
    }
}
