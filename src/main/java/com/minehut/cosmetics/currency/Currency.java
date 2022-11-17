package com.minehut.cosmetics.currency;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public enum Currency {
    GEM("GEM", Component.text("gems").color(NamedTextColor.AQUA));

    private final String id;
    private final Component display;

    Currency(String id, Component display) {
        this.id = id;
        this.display = display;
    }

    public String id() {
        return id;
    }

    public Component display() {
        return display;
    }
}
