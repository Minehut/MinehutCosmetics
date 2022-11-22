package com.minehut.cosmetics.cosmetics.collections.dev;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.particle.Particle;
import com.minehut.cosmetics.cosmetics.types.particle.base.AnimatedPixelArtParticle;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CompassBurstParticle extends AnimatedPixelArtParticle {

    public CompassBurstParticle() {
        super(Particle.COMPASS_BURST.name(), 1, "compassburst", 1, 3, 20);
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
        return Component.text("Compass Burst").color(NamedTextColor.GRAY);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.COMPASS)
                .lore(Component.empty(),
                        Component.text("Just another particle").color(NamedTextColor.WHITE),
                        Component.empty()
                )
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.DEV;
    }
}
