package com.minehut.cosmetics.cosmetics.groups.particle.implementation;

import com.minehut.cosmetics.cosmetics.CosmeticPermission;
import com.minehut.cosmetics.cosmetics.groups.particle.ParticleCosmetic;
import com.minehut.cosmetics.model.rank.PlayerRank;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RainbowSwirlParticle extends ParticleCosmetic {
    public static final String REQUIRED_RANK = "PRO";
    private static final List<Material> RAINBOW = List.of(Material.RED_WOOL, Material.ORANGE_WOOL, Material.YELLOW_WOOL, Material.LIME_WOOL, Material.LIGHT_BLUE_WOOL, Material.CYAN_WOOL, Material.PURPLE_WOOL);
    private static final int RAINBOW_COLORS = RAINBOW.size();

    private static final Component DISPLAY_NAME = MiniMessage.miniMessage().deserialize("<rainbow>Rainbow Swirl Particles");
    private static final Component[] LORE = new Component[]{Component.empty(),
            Component.text("Magical rainbow swirling around you!").color(NamedTextColor.WHITE),
            Component.empty(),
            Component.text("Requires ").color(NamedTextColor.GRAY).append(Component.text(REQUIRED_RANK)),
            Component.empty()};

    private float rotation;
    private float height;
    private boolean increase;

    private Location location;
    private Color color;

    public RainbowSwirlParticle() {
        super(com.minehut.cosmetics.cosmetics.groups.particle.Particle.RAINBOW_SWIRL.name(), Component.text("Rainbow Swirl Particles"), CosmeticPermission.hasRank(REQUIRED_RANK), 0);
    }

    @Override
    public ItemStack menuIcon() {
        final int index = (int) ((System.currentTimeMillis() / 500L) % RAINBOW_COLORS);
        final Material rainbowColor = RAINBOW.get(index);
        return ItemBuilder.of(rainbowColor)
                .display(DISPLAY_NAME)
                .lore(LORE)
                .build();
    }

    @Override
    public void update() {
        final Player player = player().orElse(null);
        if (player == null) {
            return;
        }
        Location loc = player.getLocation();
        float points = 30f;
        float dist = 360f / points;
        float radius = 0.6f;
        double angle = rotation * Math.PI / 180.0D;
        double x = radius * Math.cos(angle);
        double z = radius * Math.sin(angle);
        loc.add(x, height, z);
        Color col = Color.getHSBColor(ThreadLocalRandom.current().nextFloat(),
                0.9f, 0.9f);
        location = loc;
        color = col;
        rotation += dist;
        if (rotation >= 360f) {
            rotation = 0f;
        }
        if (increase) {
            height += 0.03f;
            if (height >= 2.1f) {
                increase = false;
            }
        } else {
            height -= 0.03f;
            if (height <= 0.05f) {
                increase = true;
            }
        }
    }

    @Override
    public void spawn(final Player player) {
        final Particle.DustOptions dustOptions = new Particle.DustOptions(org.bukkit.Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue()), 1f);
        player.spawnParticle(Particle.REDSTONE, location, 1, dustOptions);
    }
}
