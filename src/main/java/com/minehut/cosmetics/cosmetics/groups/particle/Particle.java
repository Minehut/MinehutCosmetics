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

public enum Particle implements CosmeticSupplier<ParticleCosmetic> {
    CLOUD(CloudParticle::new, true),
    FLAME(FlameParticle::new, true),
    NETHER(NetherParticle::new, true),
    RAINBOW_SWIRL(RainbowSwirlParticle::new, true),
    RAIN_CLOUD(RainCloudParticle::new, true),
    HALO(HaloParticle::new, true),
    EXPLORER(ExplorerParticle::new, true),
    QUILL(QuillParticle::new, false),
    COMPASS(CompassParticle::new, false),
    FANCY_COMPASS(FancyCompassParticle::new, false),
    REAL_PICK(RealPickParticle::new, false),
    FIRE(FireParticle::new, false),
    STAR(StarParticle::new, false),
    BLOB_DANCE(BlobDanceParticle::new, false),
    COMPASS_BURST(CompassBurstParticle::new, false),
    GOLD_PICK(GoldPickParticle::new, false);

    private final Supplier<ParticleCosmetic> supplier;
    private final boolean visible;

    Particle(final Supplier<ParticleCosmetic> supplier, boolean visible) {
        this.supplier = supplier;
        this.visible = visible;
    }

    @Override
    public ParticleCosmetic get() {
        return supplier.get();
    }

    @Override
    public boolean isVisible() {
        return visible;
    }
}
