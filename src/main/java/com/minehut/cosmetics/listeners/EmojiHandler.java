package com.minehut.cosmetics.listeners;

import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.groups.emoji.Emoji;
import com.minehut.cosmetics.cosmetics.groups.emoji.EmojiCosmetic;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentIteratorType;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public class EmojiHandler implements Listener {

    private static final Map<String, EmojiCosmetic> EMOJI_BINDINGS = new HashMap<>();

    public EmojiHandler() {
        for (final Emoji emoji : Emoji.values()) {
            final EmojiCosmetic cosmetic = emoji.get();
            EMOJI_BINDINGS.put(cosmetic.keyword(), cosmetic);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEmoji(AsyncChatEvent event) {
        if (!event.isAsynchronous()) return;

        final Component old = event.message();
        final Component fresh = event.message().replaceText(generateConfig(event.getPlayer()));

        if (old.equals(event.message())) return;
        Bukkit.getLogger().info("Got here");

        event.message(fresh);
    }


    private TextReplacementConfig generateConfig(Player player) {
        return TextReplacementConfig.builder()
                .match("\\:[a-z_]*\\:")
                .replacement((match, ignored) -> {
                    final String input = match.group();
                    final EmojiCosmetic cosmetic = EMOJI_BINDINGS.get(input);
                    if (cosmetic == null || !Permission.staff().hasAccess(player).join() && !cosmetic.permission().hasAccess(player).join()) {
                        return Component.text(input);
                    }
                    return cosmetic.component();
                })
                .build();
    }
}