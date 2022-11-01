package com.minehut.cosmetics.cosmetics.groups.equipment;

import org.bukkit.event.inventory.ClickType;

@FunctionalInterface
public interface ClickHandler {
    CosmeticSlot apply(ClickType click);

    static ClickHandler slot(CosmeticSlot slot) {
        return click -> slot;
    }

    ClickHandler HANDED = click -> {
        switch (click) {
            case RIGHT, SHIFT_RIGHT -> {
                return CosmeticSlot.OFF_HAND;
            }
            default -> {
                return CosmeticSlot.MAIN_HAND;
            }
        }
    };
}