package com.minehut.cosmetics.cosmetics.ui;

import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;

public class BookUI {

    public static final Book COSMETICS_STORE_URL = Book.book(
            Component.text("Cosmetics"),
            Component.text("Minehut"),
            Component.text("Open Cosmetics Shop ⬈").style(Style.style(NamedTextColor.BLUE, TextDecoration.UNDERLINED)).clickEvent(ClickEvent.openUrl("https://bit.ly/3TGDqMi"))
    );

    public static final Book UNOWNED_CTA = Book.book(Component.text("Minehut"), Component.text("Minehut"),
            Component.text()
                    .append(Component.text("You don't own this cosmetic yet!").color(NamedTextColor.BLACK))
                    .append(Component.newline())
                    .append(Component.text("Open Cosmetics Shop ⬈").style(Style.style(NamedTextColor.BLUE, TextDecoration.UNDERLINED)).clickEvent(ClickEvent.openUrl("https://bit.ly/3TGDqMi")))
                    .build()
    );
}
