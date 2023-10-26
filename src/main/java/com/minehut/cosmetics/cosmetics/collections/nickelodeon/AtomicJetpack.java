package com.minehut.cosmetics.cosmetics.collections.nickelodeon;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Rarity;
import com.minehut.cosmetics.cosmetics.types.wing.Wing;
import com.minehut.cosmetics.cosmetics.types.wing.WingCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class AtomicJetpack extends WingCosmetic {
    public AtomicJetpack() {
        super(Wing.ATOMIC_JETPACK.name());

    }

    @Override
    public Component name() {
        return Component.text("Atomic Jetpack")
            .color(NamedTextColor.YELLOW)
            .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.SCUTE)
            .modelData(Model.Wing.ATOMIC_JETPACK)
            .display(name())
            .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.NICK_ARCHERY_SEASON_2;
    }

    @Override
    public @NotNull Rarity rarity() {
        return Rarity.UNCOMMON;
    }
}
