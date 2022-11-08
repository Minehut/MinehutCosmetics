package com.minehut.cosmetics.listeners;

import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.groups.emoji.Emoji;
import com.minehut.cosmetics.cosmetics.groups.emoji.EmojiCosmetic;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.lang.ref.WeakReference;
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

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEmoji(AsyncChatEvent event) {
        if (!event.isAsynchronous()) return;

        final WeakReference<TextReplacementConfig> configRef = generateConfig(event.getPlayer());

        final TextReplacementConfig config = configRef.get();
        if (config != null) {
            event.message(event.message().replaceText(config));
        }
    }


    private WeakReference<TextReplacementConfig> generateConfig(Player player) {
        return new WeakReference<>(TextReplacementConfig.builder()
                .match("\\:[a-z_]*\\:")
                .replacement((match, ignored) -> {
                    final String input = match.group();
                    final EmojiCosmetic cosmetic = EMOJI_BINDINGS.get(input);
                    if (cosmetic == null || !Permission.staff().hasAccess(player).join() && !cosmetic.permission().hasAccess(player).join()) {
                        return Component.text(input);
                    }
                    return cosmetic.component();
                })
                .build());
    }
}