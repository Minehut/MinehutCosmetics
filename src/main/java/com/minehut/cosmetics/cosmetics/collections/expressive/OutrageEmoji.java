package com.minehut.cosmetics.cosmetics.collections.expressive;

import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.emoji.Emoji;
import com.minehut.cosmetics.cosmetics.types.emoji.EmojiCosmetic;
import com.minehut.cosmetics.ui.font.Fonts;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class OutrageEmoji extends EmojiCosmetic {
    public OutrageEmoji() {
        super(Emoji.OUTRAGE.name(), Component.text("Outrage Emoji"));
    }

    @Override
    public @NotNull String keyword() {
        return ":outrage:";
    }

    @Override
    public @NotNull Component component() {
        return Fonts.Emoji.OUTRAGE;
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
