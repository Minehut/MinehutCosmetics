package com.minehut.cosmetics.cosmetics.groups.emoji;

import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.groups.emoji.implementation.ClownEmoji;
import com.minehut.cosmetics.cosmetics.groups.emoji.implementation.CryEmoji;
import com.minehut.cosmetics.cosmetics.groups.emoji.implementation.EyeEmoji;
import com.minehut.cosmetics.cosmetics.groups.emoji.implementation.FireEmoji;
import com.minehut.cosmetics.cosmetics.groups.emoji.implementation.HeartEmoji;
import com.minehut.cosmetics.cosmetics.groups.emoji.implementation.LipsEmoji;
import com.minehut.cosmetics.cosmetics.groups.emoji.implementation.MinehutEmoji;
import com.minehut.cosmetics.cosmetics.groups.emoji.implementation.ObviousEmoji;
import com.minehut.cosmetics.cosmetics.groups.emoji.implementation.OutrageEmoji;
import com.minehut.cosmetics.cosmetics.groups.emoji.implementation.PartyEmoji;
import com.minehut.cosmetics.cosmetics.groups.emoji.implementation.SadEmoji;
import com.minehut.cosmetics.cosmetics.groups.emoji.implementation.SkullEmoji;
import com.minehut.cosmetics.cosmetics.groups.emoji.implementation.SmileEmoji;
import com.minehut.cosmetics.cosmetics.groups.emoji.implementation.ThumbsUpEmoji;
import com.minehut.cosmetics.cosmetics.groups.emoji.implementation.WeirdSmileEmoji;


import java.util.function.Supplier;

public enum Emoji implements CosmeticSupplier<EmojiCosmetic> {
    CLOWN(ClownEmoji::new),
    CRY(CryEmoji::new),
    EYE(EyeEmoji::new),
    FIRE(FireEmoji::new),
    HEART(HeartEmoji::new),
    LIPS(LipsEmoji::new),
    MINEHUT(MinehutEmoji::new),
    OBVIOUS(ObviousEmoji::new),
    OUTRAGE(OutrageEmoji::new),

    PARTY(PartyEmoji::new),
    SAD(SadEmoji::new),
    SKULL(SkullEmoji::new),
    SMILE(SmileEmoji::new),
    THUMBS_UP(ThumbsUpEmoji::new),
    WEIRD_SMILE(WeirdSmileEmoji::new);

    private final Supplier<EmojiCosmetic> supplier;

    Emoji(Supplier<EmojiCosmetic> supplier) {
        this.supplier = supplier;
    }

    @Override
    public EmojiCosmetic get() {
        return supplier.get();
    }
}