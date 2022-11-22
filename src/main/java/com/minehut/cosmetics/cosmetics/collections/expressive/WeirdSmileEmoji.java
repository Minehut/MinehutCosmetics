package com.minehut.cosmetics.cosmetics.collections.expressive;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.emoji.Emoji;
import com.minehut.cosmetics.cosmetics.types.emoji.EmojiCosmetic;
import com.minehut.cosmetics.ui.font.Fonts;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class WeirdSmileEmoji extends EmojiCosmetic {
    public WeirdSmileEmoji() {
        super(Emoji.WEIRD_SMILE.name());
    }

    @Override
    public @NotNull String keyword() {
        return ":weird_smile:";
    }

    @Override
    public @NotNull Component component() {
        return Fonts.Emoji.WEIRD_SMILE;
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
        return Component.text("Weird Smile Emoji");
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.GENERAL;
    }
}
