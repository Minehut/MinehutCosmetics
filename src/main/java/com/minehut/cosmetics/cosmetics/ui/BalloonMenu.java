package com.minehut.cosmetics.cosmetics.ui;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.groups.balloon.Balloon;
import com.minehut.cosmetics.cosmetics.groups.hat.Hat;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Supplier;

public class BalloonMenu extends CosmeticSubMenu {

    private static final Supplier<ItemStack> ICON = ItemBuilder.of(Material.PLAYER_HEAD)
            .display(Component.text("Balloons").decoration(TextDecoration.ITALIC, false).color(NamedTextColor.AQUA))
            .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTJkZDExZGEwNDI1MmY3NmI2OTM0YmMyNjYxMmY1NGYyNjRmMzBlZWQ3NGRmODk5NDEyMDllMTkxYmViYzBhMiJ9fX0=")
            .supplier();

    public BalloonMenu() {
        super(CosmeticCategory.BALLOON, List.of(Balloon.values()));
    }

    @Override
    public ItemStack icon() {
        return ICON.get();
    }
}
