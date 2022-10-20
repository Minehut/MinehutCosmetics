package com.minehut.cosmetics.cosmetics.ui;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.groups.hat.Hat;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;


public class HatMenu extends CosmeticSubMenu {

    public static final ItemStack ICON = ItemBuilder.of(Material.DIAMOND_HELMET)
            .flags(ItemFlag.HIDE_ATTRIBUTES)
            .display(Component.text("Hats").color(NamedTextColor.GREEN))
            .lore(
                    Component.empty(),
                    Component.text("Cool hats. Collect them all!").color(NamedTextColor.WHITE),
                    Component.empty()
            ).build();

    public HatMenu(Player player) {
        super(CosmeticCategory.HAT, player, List.of(Hat.values()));
    }
}
