package com.minehut.cosmetics.cosmetics.ui.impl.category;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.groups.balloon.Balloon;
import com.minehut.cosmetics.cosmetics.equipment.ClickHandler;
import com.minehut.cosmetics.cosmetics.equipment.CosmeticSlot;
import com.minehut.cosmetics.cosmetics.ui.CosmeticSubMenu;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BalloonMenu extends CosmeticSubMenu {

    public static final ItemStack ICON = ItemBuilder.of(Material.PLAYER_HEAD)
            .display(Component.text("Balloons", NamedTextColor.DARK_RED).decoration(TextDecoration.ITALIC, false))
            .lore(
                    Component.empty(),
                    Component.text("Ever been to a birthday party? Yeah you got the idea..", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, true),
                    Component.empty()
            )
            .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTJkZDExZGEwNDI1MmY3NmI2OTM0YmMyNjYxMmY1NGYyNjRmMzBlZWQ3NGRmODk5NDEyMDllMTkxYmViYzBhMiJ9fX0=")
            .build();

    public BalloonMenu(Player player) {
        super(CosmeticCategory.BALLOON, player, List.of(Balloon.values()), ClickHandler.slot(CosmeticSlot.BALLOON));
    }
}
