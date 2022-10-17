package com.minehut.cosmetics.cosmetics.groups.item.implementation.shovel;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticPermission;
import com.minehut.cosmetics.cosmetics.Model;
import com.minehut.cosmetics.cosmetics.groups.item.Item;
import com.minehut.cosmetics.cosmetics.groups.item.ItemCosmetic;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public class Fall2022Shovel extends ItemCosmetic {

    private static final Component DISPLAY_NAME = Component.text("Autumn's Shovel").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false);
    private static final Supplier<ItemStack> ITEM = ItemBuilder.of(Material.DIAMOND_SHOVEL)
            .display(DISPLAY_NAME)
            .lore(
                    Component.empty(),
                    Component.text("Autumn 2022").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false),
                    Component.empty()
            )
            .modelData(Model.Item.SHOVEL.FALL_2022)
            .supplier();

    public Fall2022Shovel() {
        super(
                Item.FALL_22_SHOVEL.name(),
                DISPLAY_NAME,
                CosmeticPermission.hasPurchased(CosmeticCategory.ITEM.name(), Item.FALL_22_SHOVEL.name()),
                ITEM
        );
    }

    @Override
    public ItemStack menuIcon() {
        return ITEM.get();
    }
}
