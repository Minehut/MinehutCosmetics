package com.minehut.cosmetics.cosmetics.collections.dev;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.particle.Particle;
import com.minehut.cosmetics.cosmetics.types.particle.base.AmbientPixelArtParticle;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class RealPickParticle extends AmbientPixelArtParticle {

    public RealPickParticle() {
        super(Particle.REAL_PICK.name(), 1, "realpick", 3);
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
        return Component.text("Placeholder");
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.GOLDEN_PICKAXE)
                .flags(ItemFlag.HIDE_ATTRIBUTES)
                .display(name())
                .lore(Component.empty(),
                        Component.text("Just another particle").color(NamedTextColor.WHITE),
                        Component.empty())
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.DEV;
    }
}
