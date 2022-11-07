package com.minehut.cosmetics.cosmetics.groups.item.implementation.sword;


import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticCollection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.groups.item.Item;
import com.minehut.cosmetics.cosmetics.groups.item.ItemCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public class CrusaderSword extends ItemCosmetic {

    public static final Component NAME = Component.text("Crusader Sword")
            .color(NamedTextColor.AQUA)
            .decoration(TextDecoration.ITALIC, false);

    public static final Supplier<ItemStack> ITEM = ItemBuilder.of(Material.DIAMOND_SWORD)
            .display(NAME)
            .lore(
                    Component.empty(),
                    CosmeticCollection.CRUSADER.tag(),
                    Component.empty()
            )
            .flags(ItemFlag.HIDE_ATTRIBUTES)
            .modelData(Model.Item.Sword.CRUSADER_SWORD)
            .supplier();

    public CrusaderSword() {
        super(
                Item.CRUSADER_SWORD.name(),
                NAME,
                Permission.hasPurchased(CosmeticCategory.ITEM.name(), Item.CRUSADER_SWORD.name()),
                Permission.deny()
        );
    }

    @Override
    public ItemStack item() {
        return ITEM.get();
    }
}