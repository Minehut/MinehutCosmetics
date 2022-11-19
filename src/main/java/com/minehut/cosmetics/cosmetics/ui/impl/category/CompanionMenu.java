package com.minehut.cosmetics.cosmetics.ui.impl.category;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.types.companion.Companion;
import com.minehut.cosmetics.cosmetics.properties.ClickHandler;
import com.minehut.cosmetics.cosmetics.properties.CosmeticSlot;
import com.minehut.cosmetics.cosmetics.ui.CosmeticSubMenu;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CompanionMenu extends CosmeticSubMenu {

    public static final ItemStack ICON = ItemBuilder.of(Material.PLAYER_HEAD)
            .display(Component.text("Companions", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false))
            .lore(
                    Component.empty(),
                    Component.text("Friends to join you on your adventures!", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, true),
                    Component.empty()
            )
            .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzQ4OGZhZjM2ZjIyNzJjMTQ1MzIxYjVhNDNlMzljZmEwNWJhNDM5ZGE3ODFiNmE5MTc2NGYzZGIxYWY3MTUyMyJ9fX0=")
            .build();

    public CompanionMenu(Player player) {
        super(CosmeticCategory.COMPANION, player, List.of(Companion.values()), ClickHandler.slot(CosmeticSlot.COMPANION));
    }
}
