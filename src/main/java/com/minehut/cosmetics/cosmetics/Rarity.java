package com.minehut.cosmetics.cosmetics;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public enum Rarity {
    UNCOMMON(Component.text("Uncommon").color(NamedTextColor.DARK_GREEN), 50),
    RARE(Component.text("Rare").color(NamedTextColor.AQUA), 75),
    EPIC(Component.text("Epic").color(NamedTextColor.LIGHT_PURPLE), 125),
    LEGENDARY(Component.text("Legendary").color(NamedTextColor.GOLD), 250),
    MYTHIC(Component.text("Mythic").color(NamedTextColor.DARK_PURPLE), 375);

    private final Component display;
    private final int slavageValue;

    Rarity(Component display, int salvageValue) {
        this.display = display;
        this.slavageValue = salvageValue;
    }

    public Component display() {
        return display;
    }

    public int salvageValue() {
        return slavageValue;
    }
}
