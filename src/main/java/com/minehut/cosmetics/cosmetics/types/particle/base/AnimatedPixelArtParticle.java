package com.minehut.cosmetics.cosmetics.types.particle.base;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public abstract class AnimatedPixelArtParticle extends PixelArtParticle {
    private final List<Location> locations = new ArrayList<>();
    private final List<Particle.DustOptions> options = new ArrayList<>();
    private final int interval;
    private final int frameLength;
    private int frameTick;
    private int intervalTick;
    private int frameLengthTick;

    protected AnimatedPixelArtParticle(
            final String id,
            final int delay,
            final String art,
            final int frames,
            final int frameLength,
            final int interval
    ) {
        super(id, delay, art, frames);
        this.frameLength = frameLength;
        this.interval = interval;
        this.intervalTick = interval;
    }

    @Override
    public void update() {
        final Player player = player().orElse(null);
        if (player == null) {
            return;
        }
        --intervalTick;
        if (intervalTick > 0) {
            locations.clear();
            options.clear();
            return;
        }
        if (frameLengthTick == 0) {
            locations.clear();
            options.clear();
            final Location origin = player.getEyeLocation().clone().add(0, 0.5, 0);
            final Vector direction = player.getEyeLocation().getDirection().getCrossProduct(new Vector(0, 1, 0)).normalize();
            final double directionX = direction.getX();
            final double directionZ = direction.getZ();
            final double stepSize = 0.1;
            final int width = getWidth();
            final int offset = width / 2;
            final double offsetX = -offset * stepSize * directionX;
            final double offsetZ = -offset * stepSize * directionZ;
            for (int x = 0; x < getWidth(); ++x) {
                for (int y = 0; y < getHeight(); ++y) {
                    if (!hasPixel(x, y)) {
                        continue;
                    }
                    final double xPos = stepSize * directionX * x + offsetX;
                    final double yPos = stepSize * y;
                    final double zPos = stepSize * directionZ * x + offsetZ;
                    locations.add(origin.clone().add(xPos, yPos, zPos));
                    options.add(new Particle.DustOptions(Color.fromRGB(getColor(x, y)), 0.5f));
                }
            }
        }
        ++frameLengthTick;
        if (frameLengthTick == frameLength) {
            ++frameTick;
            if (frameTick == frames) {
                frameTick = 0;
                intervalTick = interval;
            }
            nextFrame();
            frameLengthTick = 0;
        }
    }

    @Override
    public void spawn(Player player) {
        final int particles = locations.size();
        for (int i = 0; i < particles; ++i) {
            final Location location = locations.get(i);
            final Particle.DustOptions option = options.get(i);
            player.spawnParticle(Particle.REDSTONE, location, 1, option);
        }
    }
}
