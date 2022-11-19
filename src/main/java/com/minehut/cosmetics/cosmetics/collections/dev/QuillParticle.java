package com.minehut.cosmetics.cosmetics.collections.dev;

import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.particle.Particle;
import com.minehut.cosmetics.cosmetics.types.particle.base.AmbientPixelArtParticle;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class QuillParticle extends AmbientPixelArtParticle {
    public QuillParticle() {
        super(Particle.QUILL.name(), Component.text("Placeholder"), 1, "quill", 3);
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
    public ItemStack menuIcon() {
        return new ItemStack(Material.AMETHYST_SHARD);
    }
}
