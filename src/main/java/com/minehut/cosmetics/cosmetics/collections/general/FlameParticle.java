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

public class FlameParticle extends ParticleCosmetic {
    public static final String REQUIRED_RANK = "VIP";
    private Location location;

    public FlameParticle() {
        super(Particle.FLAME.name(), 1);
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
        return Component.text("Flame Particles").color(NamedTextColor.GOLD);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.BLAZE_POWDER)
                .lore(Component.empty(),
                        Component.text("A ball of flame around your feet.").color(NamedTextColor.WHITE),
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
        location = player.getLocation().add(0, 0.25, 0);
    }

    @Override
    public void spawn(final Player player) {
        player.spawnParticle(Particle.FLAME, location, 12, 0.26f, 0.2f, 0.26f, 0.006f);
    }
}
