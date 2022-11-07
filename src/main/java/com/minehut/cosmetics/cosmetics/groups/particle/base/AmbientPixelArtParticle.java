package com.minehut.cosmetics.cosmetics.groups.particle.base;

import com.minehut.cosmetics.cosmetics.Permission;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AmbientPixelArtParticle extends PixelArtParticle {
    private final int lifespan;
    private final List<Location> locations = new ArrayList<>();
    private final List<Particle.DustOptions> options = new ArrayList<>();
    private int lifespanTick = 0;

    protected AmbientPixelArtParticle(
            final String id,
            final Component name,
            final Permission permission,
            final Permission visibility,
            final int delay,
            final String art,
            final int lifespan
    ) {
        super(id, name, permission, visibility, delay, art, 1);
        this.lifespan = lifespan;
    }

    @Override
    public void update() {
        final Player player = player().orElse(null);
        if (player == null) {
            return;
        }
        if (lifespanTick++ % lifespan != 0) {
            return;
        }
        locations.clear();
        options.clear();
        final Location origin = player.getEyeLocation().clone().add(
                (ThreadLocalRandom.current().nextInt(2) == 0 ? -1 : 1) * ThreadLocalRandom.current().nextDouble(2),
                (ThreadLocalRandom.current().nextInt(2) == 0 ? -1 : 1) * ThreadLocalRandom.current().nextDouble(1),
                (ThreadLocalRandom.current().nextInt(2) == 0 ? -1 : 1) * ThreadLocalRandom.current().nextDouble(2)
        );
        final double randomX = 2 * ThreadLocalRandom.current().nextDouble() - 1;
        final double randomZ = 2 * ThreadLocalRandom.current().nextDouble() - 1;
        final Vector direction = new Vector(randomX == 0 ? 1 : randomX, 0, randomZ == 0 ? 1 : randomZ).normalize();
        final double directionX = direction.getX();
        final double directionZ = direction.getZ();
        final double stepSize = 0.1;
        for (int x = 0; x < getWidth(); ++x) {
            for (int y = 0; y < getHeight(); ++y) {
                if (!hasPixel(x, y)) {
                    continue;
                }
                final double xPos = stepSize * directionX * x;
                final double yPos = stepSize * y;
                final double zPos = stepSize * directionZ * x;
                locations.add(origin.clone().add(xPos, yPos, zPos));
                options.add(new Particle.DustOptions(Color.fromRGB(getColor(x, y)), 0.5f));
            }
        }
    }

    @Override
    public void spawn(final Player player) {
        final int particles = locations.size();
        for (int i = 0; i < particles; ++i) {
            final Location location = locations.get(i);
            final Particle.DustOptions option = options.get(i);
            player.spawnParticle(Particle.REDSTONE, location, 1, option);
        }
    }
}
