package com.minehut.cosmetics.cosmetics.groups.particle;

import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.groups.particle.implementation.BlobDanceParticle;
import com.minehut.cosmetics.cosmetics.groups.particle.implementation.CloudParticle;
import com.minehut.cosmetics.cosmetics.groups.particle.implementation.CompassBurstParticle;
import com.minehut.cosmetics.cosmetics.groups.particle.implementation.CompassParticle;
import com.minehut.cosmetics.cosmetics.groups.particle.implementation.ExplorerParticle;
import com.minehut.cosmetics.cosmetics.groups.particle.implementation.FancyCompassParticle;
import com.minehut.cosmetics.cosmetics.groups.particle.implementation.FireParticle;
import com.minehut.cosmetics.cosmetics.groups.particle.implementation.FlameParticle;
import com.minehut.cosmetics.cosmetics.groups.particle.implementation.GoldPickParticle;
import com.minehut.cosmetics.cosmetics.groups.particle.implementation.HaloParticle;
import com.minehut.cosmetics.cosmetics.groups.particle.implementation.NetherParticle;
import com.minehut.cosmetics.cosmetics.groups.particle.implementation.QuillParticle;
import com.minehut.cosmetics.cosmetics.groups.particle.implementation.RainCloudParticle;
import com.minehut.cosmetics.cosmetics.groups.particle.implementation.RainbowSwirlParticle;
import com.minehut.cosmetics.cosmetics.groups.particle.implementation.RealPickParticle;
import com.minehut.cosmetics.cosmetics.groups.particle.implementation.StarParticle;

import java.util.function.Supplier;

public enum Particle implements CosmeticSupplier {
    CLOUD(CloudParticle::new),
    FLAME(FlameParticle::new),
    NETHER(NetherParticle::new),
    RAINBOW_SWIRL(RainbowSwirlParticle::new),
    RAIN_CLOUD(RainCloudParticle::new),
    HALO(HaloParticle::new),
    EXPLORER(ExplorerParticle::new),
    QUILL(QuillParticle::new),
    COMPASS(CompassParticle::new),
    FANCY_COMPASS(FancyCompassParticle::new),
    REAL_PICK(RealPickParticle::new),
    FIRE(FireParticle::new),
    STAR(StarParticle::new),
    BLOB_DANCE(BlobDanceParticle::new),
    COMPASS_BURST(CompassBurstParticle::new),
    GOLD_PICK(GoldPickParticle::new);

    private final Supplier<ParticleCosmetic> supplier;

    Particle(final Supplier<ParticleCosmetic> supplier) {
        this.supplier = supplier;
    }

    @Override
    public ParticleCosmetic get() {
        return supplier.get();
    }
}
