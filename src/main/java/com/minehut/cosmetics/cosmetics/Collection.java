package com.minehut.cosmetics.cosmetics;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.List;
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
    CHEESE_HEADS(Component.text("Cheese Heads", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false)),
    NICK_ARCHERY_SEASON_1(Component.text("Earned from Nickelodeon Slime Blast 2023", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false),
        List.of(Component.text("Season 1 Reward", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false))),
    NICK_GOLF_SEASON_1(Component.text("Earned from Nickelodeon Golf 2023", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false),
        List.of(Component.text("Season 1 Reward", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false))),
    NICK_ESCAPE_SEASON_1(Component.text("Earned from Nickelodeon Escape 2023", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false),
        List.of(Component.text("Season 1 Reward", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false))),
    NICK_ARCHERY_SEASON_2(Component.text("Earned from Nickelodeon Slime Blast 2023", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false),
        List.of(Component.text("Season 2 Reward", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false)));

    private final Component tag;
    private final List<Component> lore;

    Collection(Component tag) {
        this(tag, List.of());
    }

    Collection(Component tag, List<Component> lore) {
        this.tag = tag;
        this.lore = lore;
    }


    public Component display() {
        return tag;
    }

    public List<Component> lore() {
        return lore;
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