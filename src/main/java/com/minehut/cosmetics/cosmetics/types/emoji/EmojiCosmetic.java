package com.minehut.cosmetics.cosmetics.types.emoji;

import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.ui.font.Fonts;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class EmojiCosmetic extends Cosmetic {

    protected EmojiCosmetic(String id) {
        super(id, CosmeticCategory.EMOJI);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.PAPER).display(component()).build();
    }

    public abstract @NotNull String keyword();

    public @NotNull Component component() {
        return Component.text(characters()).color(NamedTextColor.WHITE).font(Fonts.Font.EMOJI);
    }

    public abstract String characters();

    @Override
    public int salvageAmount() {
        return 0;
    }
}