package com.minehut.cosmetics.cosmetics.collections.expressive;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.emoji.Emoji;
import com.minehut.cosmetics.cosmetics.types.emoji.EmojiCosmetic;
import com.minehut.cosmetics.ui.font.Fonts;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class SkullEmoji extends EmojiCosmetic {
    public SkullEmoji() {
        super(Emoji.SKULL.name());
    }

    @Override
    public @NotNull String keyword() {
        return ":skull:";
    }

    @Override
    public String characters() {
        return Fonts.Emoji.SKULL;
    }

    @Override
    public Permission visibility() {
        return Permission.none();
    }

    @Override
    public Component name() {
        return Component.text("Skull Emoji");
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.GENERAL;
    }
}
