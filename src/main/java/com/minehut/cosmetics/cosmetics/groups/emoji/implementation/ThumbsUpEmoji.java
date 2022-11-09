package com.minehut.cosmetics.cosmetics.groups.emoji.implementation;

import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.groups.emoji.Emoji;
import com.minehut.cosmetics.cosmetics.groups.emoji.EmojiCosmetic;
import com.minehut.cosmetics.ui.font.Fonts;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class ThumbsUpEmoji extends EmojiCosmetic {
    public ThumbsUpEmoji() {
        super(Emoji.THUMBS_UP.name(), Component.text("Thumbs Up Emoji"));
    }

    @Override
    public @NotNull String keyword() {
        return ":thumbs_up:";
    }

    @Override
    public @NotNull Component component() {
        return Fonts.Emoji.THUMBS_UP;
    }

    @Override
    public Permission permission() {
        return Permission.hasPurchased(category(), id());
    }

    @Override
    public Permission visibility() {
        return Permission.none();
    }
}
