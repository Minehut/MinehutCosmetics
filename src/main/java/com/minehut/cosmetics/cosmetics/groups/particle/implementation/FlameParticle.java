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

public class FlameParticle extends ParticleCosmetic {
    public static final String REQUIRED_RANK = "VIP";
    private static final ItemStack ITEM = ItemBuilder.of(Material.BLAZE_POWDER)
            .display(Component.text("Flame Particles").color(NamedTextColor.GOLD))
            .lore(Component.empty(),
                    Component.text("A ball of flame around your feet.").color(NamedTextColor.WHITE),
                    Component.empty(),
                    Component.text("Requires ").color(NamedTextColor.GRAY).append(Component.text(REQUIRED_RANK)),
                    Component.empty())
            .build();
    private Location location;

    public FlameParticle() {
        super(Particle.FLAME.name(),
                Component.text("Flame Particles"),
                Permission.hasRank(REQUIRED_RANK),
                Permission.none(),
                1);
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
        location = player.getLocation().add(0, 0.25, 0);
    }

    @Override
    public void spawn(final Player player) {
        player.spawnParticle(Particle.FLAME, location, 12, 0.26f, 0.2f, 0.26f, 0.006f);
    }
}
