package com.minehut.cosmetics.cosmetics.collections.autumn2022;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.wing.WingCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public class Fall22Wings extends WingCosmetic {

    public static final Component NAME = Component.text("Wings of Autumn")
            .color(NamedTextColor.AQUA)
            .decoration(TextDecoration.ITALIC, false);

    public static final Supplier<ItemStack> ITEM = ItemBuilder.of(Material.SCUTE)
            .display(NAME)
            .lore(
                    Component.empty(),
                    Collection.FALL_22.tag(),
                    Component.empty()
            )
            .modelData(Model.Wing.FALL_22)
            .supplier();

    public Fall22Wings() {
        super(com.minehut.cosmetics.cosmetics.types.wing.Wing.FALL_22.name(), NAME);
    }

    @Override
    public Permission permission() {
        return Permission.hasPurchased(this);
    }

    @Override
    public Permission visibility() {
        return Permission.collectionIsActive(Collection.FALL_22);
    }

    @Override
    public ItemStack menuIcon() {
        return ITEM.get();
    }
}