package com.minehut.cosmetics.cosmetics.collections.beta;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.Rarity;
import com.minehut.cosmetics.cosmetics.types.companion.CompanionCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class CompieCompanion extends CompanionCosmetic {
    public CompieCompanion() {
        super(
                com.minehut.cosmetics.cosmetics.types.companion.Companion.EXPLORER.name(),
                new Vector(0, -1, 0),
                true,
                true,
                false
        );
    }

    @Override
    public Permission visibility() {
        return Permission.collectionIsActive(Collection.BETA);
    }

    @Override
    public Component name() {
        return Component.text("Compie-chan").color(rarity().display().color());
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.SCUTE)
                .display(name())
                .modelData(Model.Companion.COMPIE_CHAN)
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.BETA;
    }

    @Override
    public @NotNull Rarity rarity() {
        return Rarity.RARE;
    }
}
