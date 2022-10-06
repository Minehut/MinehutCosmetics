package com.minehut.cosmetics.cosmetics.groups.particle.implementation;

import com.minehut.cosmetics.cosmetics.CosmeticPermission;
import com.minehut.cosmetics.cosmetics.groups.particle.Particle;
import com.minehut.cosmetics.cosmetics.groups.particle.base.AmbientPixelArtParticle;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class QuillParticle extends AmbientPixelArtParticle {
    public QuillParticle() {
        super(Particle.QUILL.name(), Component.text("Placeholder"), CosmeticPermission.isStaff(), 1, "quill", 3);
    }

    @Override
    public ItemStack menuIcon() {
        return new ItemStack(Material.AMETHYST_SHARD);
    }
}
