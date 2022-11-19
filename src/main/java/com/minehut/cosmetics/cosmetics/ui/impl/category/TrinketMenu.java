package com.minehut.cosmetics.cosmetics.ui.impl.category;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.properties.ClickHandler;
import com.minehut.cosmetics.cosmetics.types.trinket.Trinket;
import com.minehut.cosmetics.cosmetics.ui.CosmeticSubMenu;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class TrinketMenu extends CosmeticSubMenu {
    public static final ItemStack ICON = ItemBuilder.of(Material.NAME_TAG)
            .display(Component.text("Trinkets", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false))
            .lore(
                    Component.empty(),
                    Component.text("Item Skins to hold and display for fun!", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, true),
                    Component.empty()
            )
            .build();

    public TrinketMenu(Player player) {
        super(CosmeticCategory.TRINKET, player, List.of(Trinket.values()), ClickHandler.HANDED);
    }
}
