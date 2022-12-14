package com.minehut.cosmetics.cosmetics.collections.general;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.particle.ParticleCosmetic;
import com.minehut.cosmetics.util.ItemBuilder;
import com.minehut.cosmetics.util.MathUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HaloParticle extends ParticleCosmetic {
    public static final String REQUIRED_RANK = "LEGEND";

    private final List<Location> locations = new ArrayList<>();

    public HaloParticle() {
        super(com.minehut.cosmetics.cosmetics.types.particle.Particle.HALO.name(), 2);
    }

    @Override
    public Permission permission() {
        return Permission.hasRank(REQUIRED_RANK);
    }

    @Override
    public Permission visibility() {
        return Permission.none();
    }

    @Override
    public Component name() {
        return Component.text("Halo Particles").color(NamedTextColor.YELLOW);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.FEATHER)
                .lore(Component.empty(),
                        Component.text("*heavenly choir plays*").color(NamedTextColor.WHITE),
                        Component.empty(),
                        Component.text("Requires ").color(NamedTextColor.GRAY).append(Component.text(REQUIRED_RANK)),
                        Component.empty())
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.GENERAL;
    }

    @Override
    public void update() {
        final Player player = player().orElse(null);
        if (player == null) {
            return;
        }
        locations.clear();
        Location location = player.getEyeLocation();
        float points = 12f;
        float dist = 360f / points;
        float radius = 0.3f;
        for (float i = 0.0f; i < 360.0f; i += dist) {
            double angle = i * Math.PI / 180.0D;
            double x = radius * Math.cos(angle);
            double z = radius * Math.sin(angle);
            Location loc = new Location(null, x, 0.4 + (Math.abs(location.getPitch()) * 0.0025), z);
            Vector v = loc.toVector();
            Vector result = MathUtil.rotateVector(v, location);
            Location clone = location.clone();
            clone.add(result.getX(), result.getY(), result.getZ());
            locations.add(clone);
        }
    }

    @Override
    public void spawn(final Player player) {
        for (final Location location : locations) {
            player.spawnParticle(Particle.REDSTONE, location, 1, new Particle.DustOptions(Color.fromRGB(255, 216, 25), 1f));
        }
    }
}
