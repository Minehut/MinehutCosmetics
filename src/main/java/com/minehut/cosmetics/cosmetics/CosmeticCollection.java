package com.minehut.cosmetics.cosmetics;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

public enum CosmeticCollection {
    BETA(Component.text("Minehut Cosmetic: Beta").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false)),
    FALL_22(Component.text("Autumn 2022").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false)),
    SPOOKY_22(Component.text("Spooktacular 2022").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false)),
    MAID(Component.text("Maids of Minehut").color(TextColor.color(255, 182, 193)).decoration(TextDecoration.ITALIC, false)),
    CRUSADER(Component.text("Crusader Collection").color(TextColor.color(238, 232, 170)).decoration(TextDecoration.ITALIC, false)),
    ;

    private final Component tag;

    CosmeticCollection(Component tag) {
        this.tag = tag;
    }

    public Component tag() {
        return tag;
    }
}