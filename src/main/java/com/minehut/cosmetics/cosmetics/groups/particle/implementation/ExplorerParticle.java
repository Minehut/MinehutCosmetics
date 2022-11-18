package com.minehut.cosmetics.cosmetics.groups.particle.implementation;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.groups.particle.Particle;
import com.minehut.cosmetics.cosmetics.groups.particle.base.AmbientPixelArtParticle;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ExplorerParticle extends AmbientPixelArtParticle {
    public static final ItemStack ITEM = ItemBuilder.of(Material.COMPASS)
            .display(Component.text("Explorer's Burst").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false))
            .lore(Component.empty(),
                    Component.text("Minehut Cosmetic: Beta").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
                    Component.empty())
            .build();

    public ExplorerParticle() {
        super(Particle.EXPLORER.name(), Component.text("Explorer’s Particle Burst"), 1, "ExplorerBurst", 6);
    }

    @Override
    public Permission permission() {
        return Permission.hasPurchased(this);
    }

    @Override
    public Permission visibility() {
        return Permission.collectionIsActive(Collection.BETA);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ITEM;
    }

    @Override
    public int salvageAmount() {
        return 0;
    }
}
