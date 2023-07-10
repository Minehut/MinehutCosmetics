package com.minehut.cosmetics.cosmetics;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.Set;

public enum Collection {
    DEV(Component.text("Developer").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false)),
    BETA(Component.text("Minehut Beta").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false)),
    FALL_22(Component.text("Autumn 2022").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false)),
    SPOOKY_22(Component.text("Spooktacular 2022").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false)),
    MAID(Component.text("Maids of Minehut").color(TextColor.color(255, 182, 193)).decoration(TextDecoration.ITALIC, false)),
    CRUSADER(Component.text("Minehut Crusaders").color(TextColor.color(238, 232, 170)).decoration(TextDecoration.ITALIC, false)),
    GENERAL(Component.text("Minehut General").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)),
    DRAGON_CRATE(Component.text("Minehut Dragon Crate").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false)),
    WENDELL_AND_WILD(Component.text("From the Netflix Original Film Wendell & Wild").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false)),
    ICE(Component.text("Ice Collection").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false)),
    WINTER_2023(Component.text("Winter 2023").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false)),
    NINJA(Component.text("Ninja Collection").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false)),
    ARCADE(Component.text("Arcade Collection").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false)),
    ROYAL(Component.text("Royal Collection").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false)),
    VALENTINES_2023(Component.text("Valentines 2023").color(TextColor.color(255, 182, 193)).decoration(TextDecoration.ITALIC, false)),
    CHEESE_HEADS(Component.text("Cheese Heads", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));

    private final Component tag;

    Collection(Component tag) {
        this.tag = tag;
    }

    public Component display() {
        return tag;
    }

    private static final Set<Collection> ACTIVE = Set.of(
        GENERAL,
        MAID,
        DRAGON_CRATE,
        ICE,
        ARCADE,
        WINTER_2023
    );

    public static boolean isActive(Collection collection) {
        return ACTIVE.contains(collection);
    }
}