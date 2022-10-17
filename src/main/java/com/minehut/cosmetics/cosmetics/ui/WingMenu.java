package com.minehut.cosmetics.cosmetics.ui;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.groups.wing.Wing;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Supplier;

public class WingMenu extends CosmeticSubMenu {

    private static final Supplier<ItemStack> ICON = ItemBuilder.of(Material.FEATHER)
            .display(Component.text("Wings").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.AQUA))
            .supplier();

    public WingMenu() {
        super(CosmeticCategory.WING, List.of(Wing.values()));
    }

    @Override
    public ItemStack icon() {
        return ICON.get();
    }
}
