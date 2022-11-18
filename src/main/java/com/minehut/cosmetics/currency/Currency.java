package com.minehut.cosmetics.currency;

import com.minehut.cosmetics.ui.font.Fonts;
import net.kyori.adventure.text.Component;

public enum Currency {
    GEM("GEM", Fonts.Emoji.MINEHUT_LOGO);

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
