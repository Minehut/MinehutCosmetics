package com.minehut.cosmetics.ui.font;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.WHITE;

public class Fonts {

    public static class Font {
        public static final Key EMOJI = Key.key("minehut", "emoji");
    }

    public static class Icon {
        public static final Component COSMETICS_CTA = text("\ue003").color(WHITE);
        public static final Component COSMETICS_CTA_PREFIX = text("\uF82C\uF82A\uF828\uF822").append(COSMETICS_CTA).append(text("\uF80D\uF809"));

    }

    public static class Emoji {
        public static final Component CLOWN = text("\ue001").color(WHITE).style(Style.style().font(Font.EMOJI));
        public static final Component CRY = text("\ue002").color(WHITE).style(Style.style().font(Font.EMOJI));
    }
}