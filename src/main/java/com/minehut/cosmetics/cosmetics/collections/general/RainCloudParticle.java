package com.minehut.cosmetics.cosmetics.collections.general;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.particle.ParticleCosmetic;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class RainCloudParticle extends ParticleCosmetic {
    public static final String REQUIRED_RANK = "PRO";
    private Location location;

    public RainCloudParticle() {
        super(
                com.minehut.cosmetics.cosmetics.types.particle.Particle.RAIN_CLOUD.name(),
                1
        );
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
        return Component.text("Rain Cloud Particles").color(rarity().display().color());
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.WATER_BUCKET)
                .display(name())
                .lore(Component.empty(),
                        Component.text(":(").color(NamedTextColor.WHITE),
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
        location = player.getEyeLocation().add(0, 2, 0);
    }

    @Override
    public void spawn(final Player player) {
        player.spawnParticle(Particle.CLOUD, location, 25, 0.35f, 0.27f, 0.35f, 0.012f);
        player.spawnParticle(Particle.SMOKE_NORMAL, location, 10, 0.27f, 0.23f, 0.27f, 0.009f);
        player.spawnParticle(Particle.WATER_DROP, location, 9, 0.32f, 0.023f, 0.32f, 2f);
    }
}
