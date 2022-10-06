package com.minehut.cosmetics.cosmetics.groups.particle.implementation;

import com.minehut.cosmetics.cosmetics.CosmeticPermission;
import com.minehut.cosmetics.cosmetics.groups.particle.Particle;
import com.minehut.cosmetics.cosmetics.groups.particle.base.AmbientPixelArtParticle;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GoldPickParticle extends AmbientPixelArtParticle {
    private static final String REQUIRED = "EXPLORERS";
    public static final ItemStack ITEM = ItemBuilder.of(Material.ELYTRA)
            .display(Component.text("Placeholder").color(NamedTextColor.GRAY))
            .lore(Component.empty(),
                    Component.text("Just another particle").color(NamedTextColor.WHITE),
                    Component.empty(),
                    Component.text("Requires ").color(NamedTextColor.GRAY).append(MiniMessage.miniMessage().deserialize("<rainbow>%s".formatted(REQUIRED))),
                    Component.empty())
            .build();

    public GoldPickParticle() {
        super(Particle.GOLD_PICK.name(), Component.text("Placeholder"), CosmeticPermission.isStaff(), 1, "gold_pickaxe", 3);
    }

    @Override
    public ItemStack menuIcon() {
        return ITEM;
    }
}
