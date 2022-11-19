package com.minehut.cosmetics.cosmetics.collections.dev;

import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.particle.Particle;
import com.minehut.cosmetics.cosmetics.types.particle.base.AmbientPixelArtParticle;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GoldPickParticle extends AmbientPixelArtParticle {
    private static final String REQUIRED = "EXPLORERS";
    public static final ItemStack ITEM = ItemBuilder.of(Material.ELYTRA)
            .display(Component.text("Placeholder").color(NamedTextColor.GRAY))
            .lore(Component.empty(),
                    Component.text("Just another particle").color(NamedTextColor.WHITE),
                    Component.empty(),
                    Component.text("Requires ").color(NamedTextColor.GRAY).append(Component.text(REQUIRED)),
                    Component.empty())
            .build();

    public GoldPickParticle() {
        super(Particle.GOLD_PICK.name(), Component.text("Placeholder"), 1, "gold_pickaxe", 3);
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