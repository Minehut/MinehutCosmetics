package com.minehut.cosmetics.cosmetics.ui.icons;

import com.minehut.cosmetics.cosmetics.currency.Currency;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public class ItemIcon {
    public static final Supplier<ItemStack> GO_BACK = ItemBuilder.of(Material.DARK_OAK_DOOR)
            .display(Component.text("Go Back").color(NamedTextColor.RED))
            .supplier();

    public static final Supplier<ItemStack> SHOP_ICON = ItemBuilder.of(Material.GOLD_INGOT)
            .display(Component.text("Open Shop", NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false))
            .modelData(Model.Ui.SHOP_ICON)
            .supplier();

    public static final Supplier<ItemStack> FILLER_ICON = ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE)
            .display(Component.text(" ").decoration(TextDecoration.ITALIC, false))
            .supplier();

    public static final Supplier<ItemStack> CONFIRM = ItemBuilder.of(Material.LIME_STAINED_GLASS_PANE)
            .display(Component.text("Confirm").color(NamedTextColor.DARK_GREEN))
            .supplier();

    public static final Supplier<ItemStack> DENY = ItemBuilder.of(Material.RED_STAINED_GLASS_PANE)
            .display(Component.text("Cancel").color(NamedTextColor.RED))
            .supplier();

    public static final Supplier<ItemStack> SALVAGE_ICON = ItemBuilder.of(Material.CHIPPED_ANVIL)
            .display(Component.text("Salvage Menu").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false))
            .lore(
                    Component.empty(),
                    Component.text()
                            .append(Component.text("Salvage your cosmetics for gems").color(NamedTextColor.WHITE))
                            .append(Currency.GEM.display().color(NamedTextColor.WHITE))
                            .decoration(TextDecoration.ITALIC, false)
                            .build(),
                    Component.empty()
            )
            .supplier();

    public static final Supplier<ItemStack> GEM_SHOP_ICON = ItemBuilder.of(Material.DIAMOND)
            .modelData(Model.Ui.GEM_SHOP)
            .display(Component.text("Gem Shop").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false))
            .supplier();

    public static final Supplier<ItemStack> UNEQUIP = ItemBuilder.of(Material.BARRIER)
            .display(Component.text("Un-Equip").color(NamedTextColor.RED))
            .supplier();
}
