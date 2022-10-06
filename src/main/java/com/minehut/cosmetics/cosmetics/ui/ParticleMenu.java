package com.minehut.cosmetics.cosmetics.ui;

import com.minehut.cosmetics.util.ItemBuilder;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.groups.particle.Particle;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public class ParticleMenu extends CosmeticSubMenu {

    private static final Supplier<ItemStack> ICON = ItemBuilder.of(Material.REDSTONE)
            .display(Component.text("Particle Effects").color(NamedTextColor.RED))
            .lore(
                    Component.empty(),
                    Component.text("Particle effects that follow you around!").color(NamedTextColor.WHITE),
                    Component.empty()
            ).supplier();

    public ParticleMenu() {
        super(CosmeticCategory.PARTICLE, 1);
    }

    @Override
    public void render() {
        super.render();
        addCosmetic(
                Particle.CLOUD,
                Particle.FLAME,
                Particle.NETHER,
                Particle.RAINBOW_SWIRL,
                Particle.RAIN_CLOUD,
                Particle.HALO,
                Particle.EXPLORER
        );
    }

    @Override
    public ItemStack icon() {
        return ICON.get();
    }
}
