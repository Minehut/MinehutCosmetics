package com.minehut.cosmetics.cosmetics.types.particle;

import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.collections.dev.BlobDanceParticle;
import com.minehut.cosmetics.cosmetics.collections.dev.CompassBurstParticle;
import com.minehut.cosmetics.cosmetics.collections.dev.FancyCompassParticle;
import com.minehut.cosmetics.cosmetics.collections.dev.FireParticle;
import com.minehut.cosmetics.cosmetics.collections.dev.GoldPickParticle;
import com.minehut.cosmetics.cosmetics.collections.dev.QuillParticle;
import com.minehut.cosmetics.cosmetics.collections.dev.RealPickParticle;
import com.minehut.cosmetics.cosmetics.collections.dev.StarParticle;
import com.minehut.cosmetics.cosmetics.collections.general.CloudParticle;
import com.minehut.cosmetics.cosmetics.collections.general.CompassParticle;
import com.minehut.cosmetics.cosmetics.collections.general.ExplorerParticle;
import com.minehut.cosmetics.cosmetics.collections.general.FlameParticle;
import com.minehut.cosmetics.cosmetics.collections.general.HaloParticle;
import com.minehut.cosmetics.cosmetics.collections.general.NetherParticle;
import com.minehut.cosmetics.cosmetics.collections.general.RainCloudParticle;
import com.minehut.cosmetics.cosmetics.collections.general.RainbowSwirlParticle;

import java.util.function.Supplier;

public enum Particle implements CosmeticSupplier<ParticleCosmetic> {
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
