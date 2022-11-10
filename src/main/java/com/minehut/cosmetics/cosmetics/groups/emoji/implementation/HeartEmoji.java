package com.minehut.cosmetics.cosmetics.groups.emoji.implementation;

import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.groups.emoji.Emoji;
import com.minehut.cosmetics.cosmetics.groups.emoji.EmojiCosmetic;
import com.minehut.cosmetics.ui.font.Fonts;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class HeartEmoji extends EmojiCosmetic {
    public HeartEmoji() {
        super(Emoji.HEART.name(), Component.text("Heart Emoji"));
    }

    @Override
    public @NotNull String keyword() {
        return ":heart:";
    }

    @Override
    public @NotNull Component component() {
        return Fonts.Emoji.HEART;
    }

    @Override
    public Permission permission() {
        return Permission.hasPurchased(this);
    }

    @Override
    public Permission visibility() {
        return Permission.none();
    }
}
