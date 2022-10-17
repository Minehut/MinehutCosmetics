package com.minehut.cosmetics.cosmetics.ui;

import com.minehut.cosmetics.util.ItemBuilder;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.groups.companion.Companion;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Supplier;

public class CompanionMenu extends CosmeticSubMenu {

    public static final Supplier<ItemStack> ICON = ItemBuilder.of(Material.PLAYER_HEAD)
            .display(Component.text("Companions").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false))
            .lore(
                    Component.empty(),
                    Component.text("Pets to chill with!").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false),
                    Component.empty()
            )
            .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzQ4OGZhZjM2ZjIyNzJjMTQ1MzIxYjVhNDNlMzljZmEwNWJhNDM5ZGE3ODFiNmE5MTc2NGYzZGIxYWY3MTUyMyJ9fX0=")
            .supplier();

    public CompanionMenu() {
        super(CosmeticCategory.COMPANION, List.of(Companion.values()));
    }

    @Override
    public ItemStack icon() {
        return ICON.get();
    }
}
