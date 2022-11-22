package com.minehut.cosmetics.cosmetics.types.particle;

import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.properties.Tickable;
import com.minehut.cosmetics.util.Constants;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public abstract class ParticleCosmetic extends Cosmetic implements Tickable {
    protected final int delay;
    protected int tick;

    protected ParticleCosmetic(final String id, final int delay) {
        super(id, CosmeticCategory.PARTICLE);
        this.delay = delay;
    }

    public final void tick() {
        tick++;
        if (tick > delay) {
            onTick();
            tick = 0;
        }
    }

    private void onTick() {
        player().ifPresent((player) -> {
            update();
            // spawn particles for all players in that world
            for (final Player viewer : player.getWorld().getPlayers()) {
                // skip players who are farther than the view distance away
                if (player.getLocation().distanceSquared(viewer.getLocation()) >= Constants.PARTICLE_VIEW_DISTANCE) {
                    continue;
                }

                spawn(viewer);
            }
        });
    }

    public abstract void update();

    public abstract void spawn(final Player player);
}
