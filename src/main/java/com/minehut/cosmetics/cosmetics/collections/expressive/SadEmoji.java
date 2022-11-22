package com.minehut.cosmetics.cosmetics.collections.expressive;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.emoji.Emoji;
import com.minehut.cosmetics.cosmetics.types.emoji.EmojiCosmetic;
import com.minehut.cosmetics.ui.font.Fonts;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class SadEmoji extends EmojiCosmetic {
    public SadEmoji() {
        super(Emoji.SAD.name());
    }

    @Override
    public @NotNull String keyword() {
        return ":sad:";
    }

    @Override
    public @NotNull Component component() {
        return Fonts.Emoji.SAD;
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
        return Component.text("Sad Emoji");
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.GENERAL;
    }
}
