package com.minehut.cosmetics.cosmetics.ui.icons;

import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public class ItemIcon {
    public static final Supplier<ItemStack> GO_BACK = ItemBuilder.of(Material.DARK_OAK_DOOR)
            .display(Component.text("Go Back").color(NamedTextColor.RED))
            .supplier();
}
