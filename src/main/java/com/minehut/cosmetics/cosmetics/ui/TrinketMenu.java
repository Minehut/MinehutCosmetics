package com.minehut.cosmetics.cosmetics.ui;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.groups.trinket.Trinket;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class TrinketMenu extends CosmeticSubMenu {
    public static final ItemStack ICON = ItemBuilder.of(Material.GLISTERING_MELON_SLICE)
            .display(Component.text("Trinkets")
                    .decoration(TextDecoration.ITALIC, false)
                    .color(NamedTextColor.AQUA)
            )
            .build();

    public TrinketMenu(Player player) {
        super(CosmeticCategory.TRINKET, player, List.of(Trinket.values()));
    }
}
