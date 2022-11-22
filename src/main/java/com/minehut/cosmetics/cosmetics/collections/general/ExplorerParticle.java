package com.minehut.cosmetics.cosmetics.collections.general;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.particle.Particle;
import com.minehut.cosmetics.cosmetics.types.particle.base.AmbientPixelArtParticle;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ExplorerParticle extends AmbientPixelArtParticle {

    public ExplorerParticle() {
        super(Particle.EXPLORER.name(), 1, "ExplorerBurst", 6);
    }

    @Override
    public Permission permission() {
        return Permission.hasPurchased(this);
    }

    @Override
    public Permission visibility() {
        return Permission.collectionIsActive(Collection.BETA);
    }

    @Override
    public Component name() {
        return Component.text("Explorer's Burst")
                .color(rarity().display().color())
                .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.COMPASS)
                .display(name())
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.BETA;
    }

    @Override
    public int salvageAmount() {
        return 0;
    }
}
