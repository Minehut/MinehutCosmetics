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

public class DragonCleaver extends ItemCosmetic {

    public static final Component NAME = Component.text("Dragon Cleaver")
            .color(NamedTextColor.GOLD)
            .decoration(TextDecoration.ITALIC, false);

    public static final Supplier<ItemStack> ITEM = ItemBuilder.of(Material.DIAMOND_SWORD)
            .display(NAME)
            .lore(
                    Component.empty(),
                    Collection.MINEHUT_LEGENDARY_CRATE.tag(),
                    Component.empty()
            )
            .flags(ItemFlag.HIDE_ATTRIBUTES)
            .modelData(Model.Item.Sword.DRAGON_SWORD)
            .supplier();

    public DragonCleaver() {
        super(Item.DRAGON_SWORD.name(), NAME);
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
