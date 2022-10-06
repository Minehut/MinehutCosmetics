package com.minehut.cosmetics.cosmetics.groups.item.implementation.shield;

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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public class Fall2022Shield extends ItemCosmetic {

    private static final Component NAME = Component.text("Shield of Autumn")
            .color(NamedTextColor.GOLD)
            .decoration(TextDecoration.ITALIC, false);
    private static final Supplier<ItemStack> ITEM = ItemBuilder.of(Material.SHIELD)
            .display(NAME)
            .lore(
                    Component.empty(),
                    Component.text("Autumn 2022").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false),
                    Component.empty()
            )
            .modelData(Model.Item.SHIELD.FALL_2022)
            .supplier();

    public Fall2022Shield() {
        super(
                Item.FALL_22_SHIELD.name(),
                NAME,
                CosmeticPermission.hasPurchased(CosmeticCategory.ITEM.name(), Item.FALL_22_SHIELD.name()),
                ITEM
        );
    }

    @Override
    public ItemStack item() {
        return ITEM.get();
    }

    @Override
    public ItemStack menuIcon() {
        return ItemBuilder.of(ITEM.get())
                .flags(ItemFlag.HIDE_ATTRIBUTES)
                .build();
    }
}

