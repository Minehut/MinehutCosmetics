package com.minehut.cosmetics.cosmetics.groups.emoji.implementation;

import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.groups.emoji.Emoji;
import com.minehut.cosmetics.cosmetics.groups.emoji.EmojiCosmetic;
import com.minehut.cosmetics.ui.font.Fonts;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class EyeEmoji extends EmojiCosmetic {
    public EyeEmoji() {
        super(Emoji.EYE.name(), Component.text("Eye Emoji"));
    }

    @Override
    public @NotNull String keyword() {
        return ":eye:";
    }

    @Override
    public @NotNull Component component() {
        return Fonts.Emoji.EYE;
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
