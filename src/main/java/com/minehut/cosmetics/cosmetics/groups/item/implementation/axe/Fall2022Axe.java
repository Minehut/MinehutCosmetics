package com.minehut.cosmetics.cosmetics.groups.item.implementation.axe;

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

public class Fall2022Axe extends ItemCosmetic {

    private static final Component DISPLAY_NAME = Component.text("Autumn's Axe").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false);
    private static final Supplier<ItemStack> ITEM = ItemBuilder.of(Material.DIAMOND_AXE)
            .display(DISPLAY_NAME)
            .lore(
                    Component.empty(),
                    CosmeticCollection.FALL_22.tag(),
                    Component.empty()
            )
            .flags(ItemFlag.HIDE_ATTRIBUTES)
            .modelData(Model.Item.Axe.FALL_22)
            .supplier();

    public Fall2022Axe() {
        super(
                Item.FALL_22_AXE.name(),
                DISPLAY_NAME,
                Permission.hasPurchased(CosmeticCategory.ITEM.name(), Item.FALL_22_AXE.name()),
                Permission.none()
        );
    }

    @Override
    public ItemStack item() {
        return ITEM.get();
    }
}
