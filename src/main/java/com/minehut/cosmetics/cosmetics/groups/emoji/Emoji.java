package com.minehut.cosmetics.cosmetics.groups.emoji;

import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.groups.emoji.implementation.ClownEmoji;
import com.minehut.cosmetics.cosmetics.groups.emoji.implementation.CryEmoji;


import java.util.function.Supplier;

public enum Emoji implements CosmeticSupplier<EmojiCosmetic> {
    CLOWN(ClownEmoji::new),
    CRY(CryEmoji::new);

    private final Supplier<EmojiCosmetic> supplier;

    Emoji(Supplier<EmojiCosmetic> supplier) {
        this.supplier = supplier;
    }

    @Override
    public EmojiCosmetic get() {
        return supplier.get();
    }
}