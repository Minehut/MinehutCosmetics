package com.minehut.cosmetics.cosmetics.collections.dev;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.particle.Particle;
import com.minehut.cosmetics.cosmetics.types.particle.base.AmbientPixelArtParticle;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GoldPickParticle extends AmbientPixelArtParticle {
    private static final String REQUIRED = "EXPLORERS";

    public GoldPickParticle() {
        super(Particle.GOLD_PICK.name(), 1, "gold_pickaxe", 3);
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
    public Component name() {
        return Component.text("Gold Pickaxe").color(rarity().display().color());
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.ELYTRA)
                .display(name())
                .lore(Component.empty(),
                        Component.text("Just another particle").color(NamedTextColor.WHITE),
                        Component.empty(),
                        Component.text("Requires ").color(NamedTextColor.GRAY).append(Component.text(REQUIRED)),
                        Component.empty())
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.DEV;
    }
}
