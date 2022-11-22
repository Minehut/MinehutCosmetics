package com.minehut.cosmetics.cosmetics.collections.general;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.emoji.Emoji;
import com.minehut.cosmetics.cosmetics.types.emoji.EmojiCosmetic;
import com.minehut.cosmetics.ui.font.Fonts;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class MinehutEmoji extends EmojiCosmetic {
    public MinehutEmoji() {
        super(Emoji.MINEHUT.name());
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

    @Override
    public Component name() {
        return Component.text("Minehut Emoji");
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.GENERAL;
    }
}
