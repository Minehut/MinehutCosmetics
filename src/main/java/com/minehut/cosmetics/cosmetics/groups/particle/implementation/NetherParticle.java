package com.minehut.cosmetics.cosmetics.groups.particle.implementation;

import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.groups.particle.ParticleCosmetic;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NetherParticle extends ParticleCosmetic {
    public static final String REQUIRED_RANK = "VIP";
    private static final ItemStack ITEM = ItemBuilder.of(Material.NETHER_STAR)
            .display(Component.text("Nether Particles").color(NamedTextColor.DARK_PURPLE))
            .lore(Component.empty(),
                    Component.text("Join the dark side, we have cookies...").color(NamedTextColor.WHITE),
                    Component.text("and also cool purple particles.").color(NamedTextColor.WHITE),
                    Component.empty(),
                    Component.text("Requires ").color(NamedTextColor.GRAY).append(Component.text(REQUIRED_RANK)),
                    Component.empty())
            .build();
    private Location location;

    public NetherParticle() {
        super(com.minehut.cosmetics.cosmetics.groups.particle.Particle.NETHER.name(),
                Component.text("Nether Particles"),
                Permission.hasRank(REQUIRED_RANK),
                Permission.none(),
                2);
    }

    @Override
    public ItemStack menuIcon() {
        return ITEM;
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
