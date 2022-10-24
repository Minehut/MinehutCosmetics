package com.minehut.cosmetics.cosmetics.groups.item.implementation.sword;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticCollection;
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

public class Halloween22Scythe extends ItemCosmetic {

    public static final Component NAME = Component.text("The Night Bringer")
            .color(NamedTextColor.GOLD)
            .decoration(TextDecoration.ITALIC, false);

    public static final Supplier<ItemStack> ITEM = ItemBuilder.of(Material.DIAMOND_SWORD)
            .display(NAME)
            .lore(
                    Component.empty(),
                    CosmeticCollection.SPOOKY_22.tag(),
                    Component.empty()
            )
            .flags(ItemFlag.HIDE_ATTRIBUTES)
            .modelData(Model.Item.SWORD.HALLO_22_SCYTHE)
            .supplier();

    public Halloween22Scythe() {
        super(
                Item.HALLO_22_SCYTHE.name(),
                NAME,
                CosmeticPermission.hasPurchased(CosmeticCategory.ITEM.name(), Item.HALLO_22_SCYTHE.name())
        );
    }

    @Override
    public ItemStack item() {
        return ITEM.get();
    }
}
