package com.minehut.cosmetics.cosmetics.groups.emoji.implementation;

import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.groups.emoji.Emoji;
import com.minehut.cosmetics.cosmetics.groups.emoji.EmojiCosmetic;
import com.minehut.cosmetics.ui.font.Fonts;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class MinehutEmoji extends EmojiCosmetic {
    public MinehutEmoji() {
        super(Emoji.MINEHUT.name(), Component.text("Minehut Emoji"));
    }

    @Override
    public @NotNull String keyword() {
        return ":minehut:";
    }

    @Override
    public @NotNull Component component() {
        return Fonts.Emoji.MINEHUT_LOGO;
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
