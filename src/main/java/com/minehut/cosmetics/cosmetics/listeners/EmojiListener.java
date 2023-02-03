package com.minehut.cosmetics.cosmetics.listeners;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.config.Mode;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.emoji.Emoji;
import com.minehut.cosmetics.cosmetics.types.emoji.EmojiCosmetic;
import com.minehut.cosmetics.ui.font.Fonts;
import com.minehut.cosmetics.util.messaging.Message;
import io.papermc.paper.event.player.AsyncChatDecorateEvent;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EmojiListener implements Listener {

    private final Map<String, EmojiCosmetic> emojiBindings = new HashMap<>();
    private final Set<String> blacklisted = new HashSet<>();

    private static final LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.builder().build();

    public EmojiListener() {
        for (final Emoji emoji : Emoji.values()) {
            final EmojiCosmetic cosmetic = emoji.get();
            emojiBindings.put(cosmetic.keyword(), cosmetic);
            blacklisted.add(cosmetic.characters());
        }

        blacklisted.add(Fonts.Icon.COSMETICS_CTA);
    }

    private final HashSet<Component> blocked = new HashSet<>();

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onAsyncChat(AsyncChatEvent event) {
        final Component original = event.originalMessage();
        if ((Mode.LOBBY == Cosmetics.mode() && blocked.contains(original))
            || Mode.LOBBY != Cosmetics.mode() && containsIllegalCharacter(original)) {
            event.getPlayer().sendMessage(Message.error("Contains Illegal Characters."));
            event.setCancelled(true);
            blocked.remove(original);
            return;
        }

        // replace emojis if we're in player server mode (
        if (Mode.PLAYER_SERVER == Cosmetics.mode()) {
            event.message(replaceEmojis(event.getPlayer(), event.message()));
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void replaceEmojisOnDecorate(AsyncChatDecorateEvent event) {
        if (Mode.LOBBY != Cosmetics.mode()) {
            return;
        }

        final Component original = event.originalMessage();
        final Player player = event.player();
        if (player == null) {
            return;
        }

        // check if we should block this message
        if (containsIllegalCharacter(event.result())) {
            event.setCancelled(true);
            blocked.add(original);
            return;
        }

        event.result(replaceEmojis(player, event.result()));
    }

    private Component replaceEmojis(Player player, Component base) {
        return base.replaceText(
            TextReplacementConfig.builder()
                .match("\\:[a-z_]*\\:")
                .replacement((match, ignored) -> {
                    final String input = match.group();
                    final EmojiCosmetic cosmetic = emojiBindings.get(input);
                    if (cosmetic == null || !Permission.staff().hasAccess(player).join() && !cosmetic.permission().hasAccess(player).join()) {
                        return Component.text(input);
                    }

                    return Component.text()
                        .append(cosmetic.component().color(NamedTextColor.WHITE))
                        .hoverEvent(HoverEvent.showText(Component.text(cosmetic.keyword())))
                        .append(Component.empty().color(NamedTextColor.WHITE))
                        .build();
                })
                .build()
        );
    }

    /**
     * Check if the message contains any illegal characters before it's sent, (Emoji Characters)
     *
     * @param component to check
     * @return whether this message contained any illegal characters
     */
    private boolean containsIllegalCharacter(Component component) {
        final String message = legacySerializer.serialize(component);

        for (final String emoji : blacklisted) {
            if (message.contains(emoji)) {
                return true;
            }
        }
        return false;
    }
}