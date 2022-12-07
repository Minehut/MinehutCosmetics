package com.minehut.cosmetics.cosmetics.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class MinehutPackEvent extends Event {

    private final Player player;
    private final boolean accepted;

    public MinehutPackEvent(Player player, boolean accepted) {
        this.player = player;
        this.accepted = accepted;
    }


    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Player player() {
        return player;
    }

    public boolean accepted() {
        return accepted;
    }
}