package com.minehut.cosmetics.cosmetics.ui;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.groups.wing.Wing;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BackpieceMenu extends CosmeticSubMenu {

    public static final ItemStack ICON = ItemBuilder.of(Material.FEATHER)
            .display(Component.text("Backpieces").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.AQUA))
            .build();

    public BackpieceMenu(Player player) {
        super(CosmeticCategory.WING, player, List.of(Wing.values()));
    }
}
