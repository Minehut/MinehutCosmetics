package com.minehut.cosmetics.cosmetics.groups.emoji;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Permission;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EmojiDisplay {

    public static void open(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(Cosmetics.get(), () -> {

            Component emojiComponent = Component.empty().color(NamedTextColor.WHITE);

            int amount = 0;
            for (final Emoji emoji : Emoji.values()) {
                final EmojiCosmetic cosmetic = emoji.get();
                if (!cosmetic.permission().hasAccess(player).join() && !Permission.staff().hasAccess(player).join()) {
                    continue;
                }

                emojiComponent = emojiComponent.append(cosmetic.component().hoverEvent(HoverEvent.showText(Component.text(cosmetic.keyword()))));
                amount++;
            }

            if (amount == 0) {
                emojiComponent = Component.text("You do not own any emojis!").color(NamedTextColor.DARK_RED);
            }

            final Book emojiBook = Book.book(Component.text("Emojis"), Component.text("Emojis"),
                    Component.text()
                            .append(Component.text("Emojis").color(NamedTextColor.DARK_GREEN))
                            .append(Component.newline())
                            .append(Component.text("Owned Emojis: ").color(NamedTextColor.GRAY))
                            .append(Component.newline())
                            .append(emojiComponent)
                            .append(Component.newline())
                            .append(Component.newline())
                            .append(Component.text("Open Cosmetics Shop ⬈").style(Style.style(NamedTextColor.BLUE, TextDecoration.UNDERLINED)).clickEvent(ClickEvent.openUrl("https://bit.ly/3TGDqMi")))
                            .build()
            );


            Bukkit.getScheduler().runTask(Cosmetics.get(), () -> player.openBook(emojiBook));
        });

    }
}