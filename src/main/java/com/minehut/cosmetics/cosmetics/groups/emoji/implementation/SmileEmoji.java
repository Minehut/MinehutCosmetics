package com.minehut.cosmetics.cosmetics.groups.emoji.implementation;

import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.groups.emoji.Emoji;
import com.minehut.cosmetics.cosmetics.groups.emoji.EmojiCosmetic;
import com.minehut.cosmetics.ui.font.Fonts;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class SmileEmoji extends EmojiCosmetic {
    public SmileEmoji() {
        super(Emoji.SMILE.name(), Component.text("Smile Emoji"));
    }

    @Override
    public @NotNull String keyword() {
        return ":smile:";
    }

    @Override
    public @NotNull Component component() {
        return Fonts.Emoji.SMILE;
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
