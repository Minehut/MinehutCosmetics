package com.minehut.cosmetics.cosmetics.ui.impl.category;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.properties.ClickHandler;
import com.minehut.cosmetics.cosmetics.properties.CosmeticSlot;
import com.minehut.cosmetics.cosmetics.types.hat.Hat;
import com.minehut.cosmetics.cosmetics.ui.CosmeticSubMenu;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;


public class HatMenu extends CosmeticSubMenu {

    public static final ItemStack ICON = ItemBuilder.of(Material.IRON_HELMET)
            .flags(ItemFlag.HIDE_ATTRIBUTES)
            .display(Component.text("Hats", NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false))
            .lore(
                    Component.empty(),
                    Component.text("Hat Skins to wear!", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, true),
                    Component.empty()
            )
            .build();

    public HatMenu(Player player) {
        super(CosmeticCategory.HAT, player, List.of(Hat.values()), ClickHandler.slot(CosmeticSlot.HEAD));
    }
}
