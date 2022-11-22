package com.minehut.cosmetics.cosmetics.collections.dev;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.particle.Particle;
import com.minehut.cosmetics.cosmetics.types.particle.base.AmbientPixelArtParticle;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class QuillParticle extends AmbientPixelArtParticle {
    public QuillParticle() {
        super(Particle.QUILL.name(), 1, "quill", 3);
    }

    @Override
    public Permission permission() {
        return Permission.staff();
    }

    @Override
    public Permission visibility() {
        return Permission.deny();
    }

    @Override
    public Component name() {
        return Component.text("Placeholder");
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return new ItemStack(Material.AMETHYST_SHARD);
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.DEV;
    }
}
