package com.minehut.cosmetics.cosmetics.events;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CosmeticEntitySpawnEvent extends Event {

    private final Entity entity;

    public CosmeticEntitySpawnEvent(Entity entity) {
        this.entity = entity;
    }


    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Entity entity() {
        return entity;
    }
}