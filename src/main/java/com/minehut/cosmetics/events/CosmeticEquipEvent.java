package com.minehut.cosmetics.events;

import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.equipment.CosmeticSlot;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class CosmeticEquipEvent extends Event {

    private final UUID playerUUID;
    private final CosmeticSlot slot;
    private final @Nullable Cosmetic cosmetic;

    public CosmeticEquipEvent(UUID playerUUID, CosmeticSlot slot, @Nullable Cosmetic cosmetic) {
        this.playerUUID = playerUUID;
        this.slot = slot;
        this.cosmetic = cosmetic;
    }


    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public CosmeticSlot getSlot() {
        return slot;
    }

    public @Nullable Cosmetic getCosmetic() {
        return cosmetic;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}