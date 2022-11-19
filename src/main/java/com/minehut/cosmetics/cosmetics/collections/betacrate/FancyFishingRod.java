package com.minehut.cosmetics.cosmetics.collections.betacrate;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.item.Item;
import com.minehut.cosmetics.cosmetics.types.item.ItemCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public class FancyFishingRod extends ItemCosmetic {

    public static final Component NAME = Component.text("Fancy Fishing Rod")
            .color(NamedTextColor.GOLD)
            .decoration(TextDecoration.ITALIC, false);

    public static final Supplier<ItemStack> ITEM = ItemBuilder.of(Material.FISHING_ROD)
            .display(NAME)
            .lore(
                    Component.empty(),
                    Collection.MINEHUT_LEGENDARY_CRATE.tag(),
                    Component.empty()
            )
            .flags(ItemFlag.HIDE_ATTRIBUTES)
            .modelData(Model.Item.FishingRod.FANCY)
            .supplier();

    public FancyFishingRod() {
        super(Item.FANCY_FISHING_ROD.name(), NAME);
    }

    @Override
    public ItemStack item() {
        return ITEM.get();
    }

    @Override
    public Permission visibility() {
        return Permission.collectionIsActive(Collection.MINEHUT_LEGENDARY_CRATE);
    }
}
