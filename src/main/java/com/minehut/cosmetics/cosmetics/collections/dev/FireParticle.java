package com.minehut.cosmetics.cosmetics.collections.dev;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.particle.Particle;
import com.minehut.cosmetics.cosmetics.types.particle.base.AmbientPixelArtParticle;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FireParticle extends AmbientPixelArtParticle {

    public FireParticle() {
        super(Particle.FIRE.name(), 1, "fire", 3);
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
        return Component.text("Fire Particle").color(NamedTextColor.GRAY);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.TORCH)
                .display(name())
                .lore(Component.empty(),
                        Component.text("Just another particle").color(NamedTextColor.WHITE),
                        Component.empty())
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.DEV;
    }
}
