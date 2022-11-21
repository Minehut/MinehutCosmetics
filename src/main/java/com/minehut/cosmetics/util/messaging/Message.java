package com.minehut.cosmetics.util.messaging;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class Message {

    public static Component announcement(Component content) {
        return Component.text()
                .append(Component.text("–––––––––––––––––––––––––").color(NamedTextColor.BLACK))
                .append(Component.newline())
                .append(Component.newline())
                .append(content)
                .append(Component.newline())
                .append(Component.newline())
                .append(Component.text("–––––––––––––––––––––––––").color(NamedTextColor.BLACK))
                .build();
    }

    public static Component info(String content) {
        return minehutTag().append(Component.text(content).color(NamedTextColor.WHITE));
    }

    public static Component info(Component content) {
        return minehutTag().append(content);
    }

    public static Component error(String content) {
        return minehutTag().append(Component.text(content).color(NamedTextColor.RED));
    }

    public static Component minehutTag() {
        return Component.text("Minehut | ").color(NamedTextColor.AQUA);
    }
}
