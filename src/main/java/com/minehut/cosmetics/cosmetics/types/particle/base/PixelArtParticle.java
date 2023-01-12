package com.minehut.cosmetics.cosmetics.types.particle.base;

import com.minehut.cosmetics.cosmetics.types.particle.ParticleCosmetic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class PixelArtParticle extends ParticleCosmetic {
    protected final List<boolean[][]> pixels = new ArrayList<>();
    protected final List<int[][]> colors = new ArrayList<>();
    protected int width;
    protected int height;
    protected int frames;
    protected int frame;

    protected PixelArtParticle(
            final String id,
            final int delay,
            final String art,
            final int frames
    ) {
        super(id, delay);
        this.frames = frames;
        if (frames == 1) {
            final String resourceLocation = "cosmetics/particle/%s.png".formatted(art);
            processFrame(0, resourceLocation);
        } else {
            for (int i = 0; i < frames; ++i) {
                final String resourceLocation = "cosmetics/particle/%s/%d.png".formatted(art, i);
                processFrame(i, resourceLocation);
            }
        }
    }

    protected PixelArtParticle(
            final String id,
            final int delay,
            final String art
    ) {
        this(id, delay, art, 1);
    }

    private void processFrame(final int frame, final String resourceLocation) {
        final BufferedImage image;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(resourceLocation)));
        } catch (final IOException e) {
            e.printStackTrace();
            return;
        }
        if (this.width == 0 && this.height == 0) {
            this.width = image.getWidth();
            this.height = image.getHeight();
        }
        this.pixels.add(new boolean[height][width]);
        this.colors.add(new int[height][width]);
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                final int argb = image.getRGB(x, y);
                this.pixels.get(frame)[height - 1 - y][x] = argb >> 24 != 0;
                this.colors.get(frame)[height - 1 - y][x] = argb & 0xFFFFFF;
            }
        }
    }

    protected int getWidth() {
        return this.width;
    }

    protected int getHeight() {
        return this.height;
    }

    protected boolean hasPixel(final int x, final int y) {
        return this.pixels.get(frame)[y][x];
    }

    protected int getColor(final int x, final int y) {
        return this.colors.get(frame)[y][x];
    }

    protected void nextFrame() {
        this.frame = (this.frame == this.frames - 1) ? 0 : this.frame + 1;
    }
}
