package com.minehut.cosmetics.cosmetics.groups.emoji.implementation;

import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.groups.emoji.Emoji;
import com.minehut.cosmetics.cosmetics.groups.emoji.EmojiCosmetic;
import com.minehut.cosmetics.ui.font.Fonts;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class FireEmoji extends EmojiCosmetic {
    public FireEmoji() {
        super(Emoji.FIRE.name(), Component.text("Fire Emoji"));
    }

    @Override
    public @NotNull String keyword() {
        return ":fire:";
    }

    @Override
    public @NotNull Component component() {
        return Fonts.Emoji.FIRE;
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
