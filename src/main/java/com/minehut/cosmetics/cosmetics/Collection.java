package com.minehut.cosmetics.cosmetics;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.Set;

public enum Collection {
    DEV(Component.text("Developer").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false)),
    BETA(Component.text("Minehut Cosmetic: Beta").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false)),
    FALL_22(Component.text("Autumn 2022").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false)),
    SPOOKY_22(Component.text("Spooktacular 2022").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false)),
    MAID(Component.text("Maids of Minehut").color(TextColor.color(255, 182, 193)).decoration(TextDecoration.ITALIC, false)),
    CRUSADER(Component.text("Crusader Collection").color(TextColor.color(238, 232, 170)).decoration(TextDecoration.ITALIC, false)),
    GENERAL(Component.text("Minehut General Store").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)),
    DRAGON_CRATE(Component.text("Minehut Legendary Crate").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false)),
    WENDELL_AND_WILD(Component.text("From the Netflix Original Film Wendell & Wild").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false));


    private final Component tag;

    Collection(Component tag) {
        this.tag = tag;
    }

    public Component display() {
        return tag;
    }

    private static final Set<Collection> ACTIVE = Set.of(FALL_22, GENERAL, MAID, DRAGON_CRATE);

    public static boolean isActive(Collection collection) {
        return ACTIVE.contains(collection);
    }
}