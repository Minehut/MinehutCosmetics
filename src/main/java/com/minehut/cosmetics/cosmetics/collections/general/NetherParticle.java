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

public class NetherParticle extends ParticleCosmetic {
    public static final String REQUIRED_RANK = "VIP";
    private Location location;

    public NetherParticle() {
        super(com.minehut.cosmetics.cosmetics.types.particle.Particle.NETHER.name(), 2);
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
        return Component.text("Nether Particles").color(NamedTextColor.DARK_PURPLE);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.NETHER_STAR)
                .lore(Component.empty(),
                        Component.text("Join the dark side, we have cookies...").color(NamedTextColor.WHITE),
                        Component.text("and also cool purple particles.").color(NamedTextColor.WHITE),
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
        location = player.getLocation().add(0, 0.7, 0);
    }

    @Override
    public void spawn(final Player player) {
        player.spawnParticle(Particle.PORTAL, location, 16, 0.7f, 0.7f, 0.7f);
    }
}
