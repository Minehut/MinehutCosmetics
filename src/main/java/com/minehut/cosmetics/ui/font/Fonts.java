package com.minehut.cosmetics.ui.font;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.WHITE;

public class Fonts {

    public static class Font {
        public static final Key EMOJI = Key.key("minehut", "emoji");
        public static final Key UI = Key.key("minehut", "ui");
    }

    public static class Icon {
        public static final Component COSMETICS_CTA = text("\ue003").color(WHITE);
        public static final Component COSMETICS_CTA_PREFIX = text("\uF82C\uF82A\uF828\uF822").append(COSMETICS_CTA).append(text("\uF80D\uF809"));
        public static final Component GEM = text("\ue001").color(WHITE).style(Style.style().font(Font.UI));
    }

    public static class Emoji {
        public static final Component CLOWN = text("\ue001").color(WHITE).style(Style.style().font(Font.EMOJI));
        public static final Component CRY = text("\ue002").color(WHITE).style(Style.style().font(Font.EMOJI));
        public static final Component EYE = text("\ue003").color(WHITE).style(Style.style().font(Font.EMOJI));
        public static final Component FIRE = text("\ue004").color(WHITE).style(Style.style().font(Font.EMOJI));
        public static final Component HEART = text("\ue005").color(WHITE).style(Style.style().font(Font.EMOJI));
        public static final Component LIPS = text("\ue006").color(WHITE).style(Style.style().font(Font.EMOJI));
        public static final Component MINEHUT_LOGO = text("\ue007").color(WHITE).style(Style.style().font(Font.EMOJI));
        public static final Component OBVIOUS = text("\ue008").color(WHITE).style(Style.style().font(Font.EMOJI));
        public static final Component OUTRAGE = text("\ue009").color(WHITE).style(Style.style().font(Font.EMOJI));
        public static final Component PARTY = text("\ue00A").color(WHITE).style(Style.style().font(Font.EMOJI));
        public static final Component SAD = text("\ue00B").color(WHITE).style(Style.style().font(Font.EMOJI));
        public static final Component SKULL = text("\ue00C").color(WHITE).style(Style.style().font(Font.EMOJI));
        public static final Component SMILE = text("\ue00D").color(WHITE).style(Style.style().font(Font.EMOJI));
        public static final Component THUMBS_UP = text("\ue00E").color(WHITE).style(Style.style().font(Font.EMOJI));
        public static final Component WEIRD_SMILE = text("\ue00F").color(WHITE).style(Style.style().font(Font.EMOJI));

    }
}