package com.minehut.cosmetics.cosmetics.collections.betacrate;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
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

import java.util.function.Supplier;

public class LargePegasusWings extends WingCosmetic {
    public LargePegasusWings() {
        super(Wing.PEGASUS_LARGE.name());
    }

    @Override
    public Component name() {
        return Component.text("Large Pegasus Wings")
                .color(rarity().display().color())
                .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.SCUTE)
                .modelData(Model.Wing.LARGE_PEGASUS)
                .display(name())
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.DRAGON_CRATE;
    }

    @Override
    public @NotNull Rarity rarity() {
        return Rarity.LEGENDARY;
    }
}
