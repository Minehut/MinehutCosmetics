package com.minehut.cosmetics.cosmetics.ui.impl.category;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.properties.ClickHandler;
import com.minehut.cosmetics.cosmetics.properties.CosmeticSlot;
import com.minehut.cosmetics.cosmetics.types.particle.Particle;
import com.minehut.cosmetics.cosmetics.ui.CosmeticSubMenu;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ParticleMenu extends CosmeticSubMenu {

    public static final ItemStack ICON = ItemBuilder.of(Material.REDSTONE)
            .display(Component.text("Particle Effects", NamedTextColor.RED))
            .lore(
                    Component.empty(),
                    Component.text("Particle’s that help you stand out from the crowd!", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, true),
                    Component.empty()
            )
            .build();

    public ParticleMenu(Player player) {
        super(CosmeticCategory.PARTICLE, player, List.of(Particle.values()), ClickHandler.slot(CosmeticSlot.PARTICLE));
    }
}
