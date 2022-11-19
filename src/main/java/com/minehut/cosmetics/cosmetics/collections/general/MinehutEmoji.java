package com.minehut.cosmetics.cosmetics.collections.general;

import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.emoji.Emoji;
import com.minehut.cosmetics.cosmetics.types.emoji.EmojiCosmetic;
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
        return Permission.hasPurchased(this);
    }

    @Override
    public Permission visibility() {
        return Permission.none();
    }
}
