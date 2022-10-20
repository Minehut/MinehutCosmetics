package com.minehut.cosmetics.cosmetics.ui;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.groups.particle.Particle;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ParticleMenu extends CosmeticSubMenu {

    public static final ItemStack ICON = ItemBuilder.of(Material.REDSTONE)
            .display(Component.text("Particle Effects").color(NamedTextColor.RED))
            .lore(
                    Component.empty(),
                    Component.text("Particle effects that follow you around!").color(NamedTextColor.WHITE),
                    Component.empty()
            ).build();

    public ParticleMenu(Player player) {
        super(CosmeticCategory.PARTICLE, player, List.of(Particle.values()));
    }
}
