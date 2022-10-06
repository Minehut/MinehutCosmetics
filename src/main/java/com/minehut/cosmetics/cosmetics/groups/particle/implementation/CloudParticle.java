package com.minehut.cosmetics.cosmetics.groups.particle.implementation;

import com.minehut.cosmetics.cosmetics.CosmeticPermission;
import com.minehut.cosmetics.cosmetics.groups.particle.ParticleCosmetic;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CloudParticle extends ParticleCosmetic {
    private static final ItemStack ITEM = ItemBuilder.of(Material.LIGHT_GRAY_DYE)
            .display(Component.text("Cloud Particles").color(NamedTextColor.GRAY))
            .lore(Component.empty(),
                    Component.text("Believe it or not, I'm walking on air!").color(NamedTextColor.WHITE),
                    Component.text("(and water vapor)").color(NamedTextColor.WHITE),
                    Component.empty())
            .build();
    private final List<Location> locations = new ArrayList<>();

    public CloudParticle() {
        super(Particle.CLOUD.name(), Component.text("Cloud Particles"), CosmeticPermission.none(), 1);
    }

    @Override
    public ItemStack menuIcon() {
        return ITEM;
    }

    @Override
    public void update() {
        player().ifPresent((player) -> {
            locations.clear();
            for (int i = 0; i < 3; i++) {
                Location loc = player.getLocation().add(0, 0.25, 0).add(ThreadLocalRandom.current().nextDouble(0.1) - 0.05,
                        ThreadLocalRandom.current().nextDouble(0.1) - 0.05, ThreadLocalRandom.current().nextDouble(0.1) - 0.05);
                locations.add(loc);
            }
        });
    }

    @Override
    public void spawn(final Player player) {
        for (final Location loc : locations) {
            player.spawnParticle(Particle.CLOUD, loc, 4, 0.26f, 0.2f, 0.26f, 0.009f);
        }
    }
}
