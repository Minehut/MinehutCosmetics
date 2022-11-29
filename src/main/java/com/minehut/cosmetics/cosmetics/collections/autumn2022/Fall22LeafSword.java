package com.minehut.cosmetics.cosmetics.collections.autumn2022;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.Rarity;
import com.minehut.cosmetics.cosmetics.types.companion.Companion;
import com.minehut.cosmetics.cosmetics.types.hat.Hat;
import com.minehut.cosmetics.cosmetics.types.item.Item;
import com.minehut.cosmetics.cosmetics.types.item.ItemCosmetic;
import com.minehut.cosmetics.cosmetics.types.wing.Wing;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

public class Fall22LeafSword extends ItemCosmetic {

    public static final Permission PERMISSION = Permission.all(List.of(
            Permission.hasPurchased(CosmeticCategory.ITEM.name(), Item.FALL_22_SWORD.name()),
            Permission.hasPurchased(CosmeticCategory.ITEM.name(), Item.FALL_22_SHIELD.name()),
            Permission.hasPurchased(CosmeticCategory.ITEM.name(), Item.FALL_22_PICKAXE.name()),
            Permission.hasPurchased(CosmeticCategory.ITEM.name(), Item.FALL_22_AXE.name()),
            Permission.hasPurchased(CosmeticCategory.ITEM.name(), Item.FALL_22_SHOVEL.name()),
            Permission.hasPurchased(CosmeticCategory.ITEM.name(), Item.FALL_22_ARROW.name()),
            Permission.hasPurchased(CosmeticCategory.ITEM.name(), Item.FALL_22_BOW.name()),
            Permission.hasPurchased(CosmeticCategory.HAT.name(), Hat.FALL_22.name()),
            Permission.hasPurchased(CosmeticCategory.WING.name(), Wing.FALL_22.name()),
            Permission.hasPurchased(CosmeticCategory.COMPANION.name(), Companion.LATTE_KUN.name())
    ));

    public Fall22LeafSword() {
        super(Item.FALL_22_LEAF_SWORD.name());
    }

    @Override
    public Permission permission() {
        return PERMISSION;
    }

    @Override
    public Component name() {
        return Component.text("Fallbringer")
                .color(rarity().display().color())
                .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.DIAMOND_SWORD)
                .display(name())
                .flags(ItemFlag.HIDE_ATTRIBUTES)
                .modelData(Model.Item.Sword.FALL_22_LEAF_SWORD)
                .build();
    }

    @Override
    public @NotNull Rarity rarity() {
        return Rarity.EPIC;
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.FALL_22;
    }
}
