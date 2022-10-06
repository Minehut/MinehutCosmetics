package com.minehut.cosmetics.cosmetics.groups.particle.implementation;

import com.minehut.cosmetics.cosmetics.CosmeticPermission;
import com.minehut.cosmetics.cosmetics.groups.particle.Particle;
import com.minehut.cosmetics.cosmetics.groups.particle.base.AnimatedPixelArtParticle;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BlobDanceParticle extends AnimatedPixelArtParticle {
    public static final ItemStack ITEM = ItemBuilder.of(Material.PUFFERFISH)
            .display(Component.text("Placeholder").color(NamedTextColor.GRAY))
            .lore(Component.empty(),
                    Component.text("Just another particle").color(NamedTextColor.WHITE),
                    Component.empty())
            .build();

    public BlobDanceParticle() {
        super(Particle.BLOB_DANCE.name(), Component.text("Placeholder"), CosmeticPermission.isStaff(), 1, "blobdance", 10, 1, 1);
    }

    @Override
    public ItemStack menuIcon() {
        return ITEM;
    }
}
