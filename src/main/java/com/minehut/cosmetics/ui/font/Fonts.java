package com.minehut.cosmetics.ui.font;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.WHITE;

/**
 * TODO: Add 'Font Character' class of some kind that takes the raw string & component
 */
public class Fonts {

    public static class Font {
        public static final Key EMOJI = Key.key("minehut", "emoji");
        public static final Key UI = Key.key("minehut", "ui");
    }

    public static class Icon {
        public static final String COSMETICS_CTA = "\ue003";
        public static final Component COSMETICS_CTA_PREFIX = text("\uF82C\uF82A\uF828\uF822").append(text(COSMETICS_CTA)).append(text("\uF80D\uF809")).color(WHITE);
        public static final Component GEM = text("\ue001").color(WHITE).style(Style.style().font(Font.UI));
    }

    public static class Emoji {
        public static final String CLOWN = "\ue001";
        public static final String CRY = "\ue002";
        public static final String EYE = "\ue003";
        public static final String FIRE = "\ue004";
        public static final String HEART = "\ue005";
        public static final String LIPS = "\ue006";
        public static final String MINEHUT_LOGO = "\ue007";
        public static final String OBVIOUS = "\ue008";
        public static final String OUTRAGE = "\ue009";
        public static final String PARTY = "\ue00A";
        public static final String SAD = "\ue00B";
        public static final String SKULL = "\ue00C";
        public static final String SMILE = "\ue00D";
        public static final String THUMBS_UP = "\ue00E";
        public static final String WEIRD_SMILE = "\ue00F";
    }
}