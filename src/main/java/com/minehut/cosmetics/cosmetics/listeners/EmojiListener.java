package com.minehut.cosmetics.cosmetics.listeners;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.config.Mode;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.emoji.Emoji;
import com.minehut.cosmetics.cosmetics.types.emoji.EmojiCosmetic;
import com.minehut.cosmetics.util.Version;
import com.minehut.cosmetics.util.messaging.Message;
import io.papermc.paper.event.player.AsyncChatDecorateEvent;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
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
    private final Set<String> emojis = new HashSet<>();

    private static final LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.builder().build();

    public EmojiListener() {
        for (final Emoji emoji : Emoji.values()) {
            final EmojiCosmetic cosmetic = emoji.get();
            emojiBindings.put(cosmetic.keyword(), cosmetic);
            emojis.add(cosmetic.characters());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void cancelChat(AsyncChatEvent event) {

        if (containsIllegalCharacter(event.originalMessage())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Message.error("Illegal Characters."));
            return;
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void replaceEmojisOnChat(AsyncChatEvent event) {
        if (!event.isAsynchronous() || Mode.PLAYER_SERVER != Cosmetics.mode()) {
            return;
        }

        final Component replaced = event.message().replaceText(generateConfig(event.getPlayer(), NamedTextColor.WHITE));
        event.getPlayer().sendMessage(replaced);
        event.message(replaced);
    }

    @SuppressWarnings("UnstableApiUsage")
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void replaceEmojisOnDecorate(AsyncChatDecorateEvent event) {
        if (!Version.V_1_19.isSupported() || !event.isAsynchronous() || Mode.LOBBY != Cosmetics.mode()) {
            return;
        }

        final Component result = event.originalMessage().replaceText(generateConfig(event.player(), NamedTextColor.WHITE));
        event.result(result);
    }


    private TextReplacementConfig generateConfig(Player player, TextColor color) {
        return TextReplacementConfig.builder()
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
                            .append(Component.empty().color(color))
                            .build();
                })
                .build();
    }

    /**
     * Check if the message contains any illegal characters before it's sent, (Emoji Characters)
     *
     * @param component to check
     * @return whether this message contained any illegal characters
     */
    private boolean containsIllegalCharacter(Component component) {
        final String message = legacySerializer.serialize(component);

        for (final String emoji : emojis) {
            if (message.contains(emoji)) {
                return true;
            }
        }
        return false;
    }
}