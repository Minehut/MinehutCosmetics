package com.minehut.cosmetics.cosmetics.groups.particle.implementation;

import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.groups.particle.Particle;
import com.minehut.cosmetics.cosmetics.groups.particle.base.AmbientPixelArtParticle;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class RealPickParticle extends AmbientPixelArtParticle {
    public static final ItemStack ITEM = ItemBuilder.of(Material.GOLDEN_PICKAXE)
            .flags(ItemFlag.HIDE_ATTRIBUTES)
            .display(Component.text("Placeholder").color(NamedTextColor.GRAY))
            .lore(Component.empty(),
                    Component.text("Just another particle").color(NamedTextColor.WHITE),
                    Component.empty())
            .build();

    public RealPickParticle() {
        super(Particle.REAL_PICK.name(), Component.text("Placeholder"), 1, "realpick", 3);
    }

    @Override
    public Permission permission() {
        return Permission.staff();
    }

    @Override
    public Permission visibility() {
        return Permission.deny();
    }

    @Override
    public ItemStack menuIcon() {
        return ITEM;
    }
}
