package com.minehut.cosmetics.cosmetics.ui;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.equipment.ClickHandler;
import com.minehut.cosmetics.cosmetics.equipment.CosmeticSlot;
import com.minehut.cosmetics.cosmetics.groups.wing.Wing;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BackpieceMenu extends CosmeticSubMenu {

    public static final ItemStack ICON = ItemBuilder.of(Material.IRON_CHESTPLATE)
            .flags(ItemFlag.HIDE_ATTRIBUTES)
            .display(Component.text("Backpieces", NamedTextColor.DARK_GREEN).decoration(TextDecoration.ITALIC, false))
            .lore(
                    Component.empty(),
                    Component.text("Item Skins to throw on your back!", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, true),
                    Component.empty()
            )
            .build();

    public BackpieceMenu(Player player) {
        super(CosmeticCategory.WING, player, List.of(Wing.values()), ClickHandler.slot(CosmeticSlot.BACK));
    }
}
