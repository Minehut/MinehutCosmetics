package com.minehut.cosmetics.listeners;

import com.minehut.cosmetics.ui.font.Fonts;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.Style;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public class EmojiListener implements Listener {

    private static final Map<String, Component> EMOJIS = new HashMap<>();

    private static final TextReplacementConfig TEXT_REPLACEMENT_CONFIG = TextReplacementConfig.builder()
            .match("\\:[a-z_]*\\:")
            .replacement((result, ignored) -> EMOJIS.get(result.group()))
            .build();

    public EmojiListener() {
        EMOJIS.put(":clown:", Fonts.Emoji.CLOWN);
        EMOJIS.put(":cry:", Fonts.Emoji.CRY);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEmoji(AsyncChatEvent event) {
        event.message(event.message().replaceText(TEXT_REPLACEMENT_CONFIG));
    }
}