package com.minehut.cosmetics.cosmetics.groups.emoji.implementation;

import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.groups.emoji.Emoji;
import com.minehut.cosmetics.cosmetics.groups.emoji.EmojiCosmetic;
import com.minehut.cosmetics.ui.font.Fonts;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class CryEmoji extends EmojiCosmetic {
    public CryEmoji() {
        super(Emoji.CRY.name(), Component.text("Cry Emoji"));
    }

    @Override
    public @NotNull String keyword() {
        return ":cry:";
    }

    @Override
    public @NotNull Component component() {
        return Fonts.Emoji.CRY;
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
