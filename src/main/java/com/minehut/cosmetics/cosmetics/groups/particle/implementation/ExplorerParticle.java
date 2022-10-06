package com.minehut.cosmetics.cosmetics.groups.particle.implementation;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticPermission;
import com.minehut.cosmetics.cosmetics.groups.particle.Particle;
import com.minehut.cosmetics.cosmetics.groups.particle.base.AmbientPixelArtParticle;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ExplorerParticle extends AmbientPixelArtParticle {
    public static final ItemStack ITEM = ItemBuilder.of(Material.COMPASS)
            .display(Component.text("Explorer's Burst").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false))
            .lore(Component.empty(),
                    Component.text("Minehut Cosmetic: Beta").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
                    Component.empty())
            .build();

    public ExplorerParticle() {
        super(
                Particle.EXPLORER.name(),
                Component.text("Explorer’s Particle Burst"),
                CosmeticPermission.hasPurchased(CosmeticCategory.PARTICLE.name(), Particle.EXPLORER.name()),
                1,
                "ExplorerBurst",
                6
        );
    }

    @Override
    public ItemStack menuIcon() {
        return ITEM;
    }
}
