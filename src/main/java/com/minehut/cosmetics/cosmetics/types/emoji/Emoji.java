package com.minehut.cosmetics.cosmetics.types.emoji;

import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.collections.expressive.ClownEmoji;
import com.minehut.cosmetics.cosmetics.collections.expressive.CryEmoji;
import com.minehut.cosmetics.cosmetics.collections.expressive.EyeEmoji;
import com.minehut.cosmetics.cosmetics.collections.expressive.FireEmoji;
import com.minehut.cosmetics.cosmetics.collections.expressive.LipsEmoji;
import com.minehut.cosmetics.cosmetics.collections.expressive.ObviousEmoji;
import com.minehut.cosmetics.cosmetics.collections.expressive.OutrageEmoji;
import com.minehut.cosmetics.cosmetics.collections.expressive.PartyEmoji;
import com.minehut.cosmetics.cosmetics.collections.expressive.SadEmoji;
import com.minehut.cosmetics.cosmetics.collections.expressive.SkullEmoji;
import com.minehut.cosmetics.cosmetics.collections.expressive.WeirdSmileEmoji;
import com.minehut.cosmetics.cosmetics.collections.general.HeartEmoji;
import com.minehut.cosmetics.cosmetics.collections.general.MinehutEmoji;
import com.minehut.cosmetics.cosmetics.collections.general.SmileEmoji;
import com.minehut.cosmetics.cosmetics.collections.general.ThumbsUpEmoji;

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