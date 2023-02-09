package com.minehut.cosmetics.cosmetics.crates.impl;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.Rarity;
import com.minehut.cosmetics.cosmetics.crates.Crate;
import com.minehut.cosmetics.cosmetics.crates.CrateType;
import com.minehut.cosmetics.cosmetics.crates.WeightedTable;
import com.minehut.cosmetics.cosmetics.types.balloon.Balloon;
import com.minehut.cosmetics.cosmetics.types.companion.Companion;
import com.minehut.cosmetics.cosmetics.types.hat.Hat;
import com.minehut.cosmetics.cosmetics.types.item.Item;
import com.minehut.cosmetics.cosmetics.types.trinket.Trinket;
import com.minehut.cosmetics.cosmetics.types.wing.Wing;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import com.minehut.cosmetics.util.structures.Pair;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HeartfeltCrate extends Crate {

    private static final WeightedTable<CrateEntry> table = WeightedTable.<CrateEntry>builder()
        .register(CrateEntry.of(Rarity.UNCOMMON, List.of(
            Pair.of(Hat.VALENTINE_HEADBAND, 1),
            Pair.of(Balloon.VALENTINE, 1)
        )), 40)
        .register(CrateEntry.of(Rarity.RARE, List.of(
            Pair.of(Hat.VALENTINES_SUNGLASSES, 1),
            Pair.of(Item.VALENTINE_PICKAXE, 1)
        )), 35)
        .register(CrateEntry.of(Rarity.EPIC, List.of(
            Pair.of(Trinket.VALENTINE_STAFF, 1),
            Pair.of(Companion.VALENTINE_PET, 1)
        )), 20)
        .register(CrateEntry.of(Rarity.LEGENDARY, List.of(
            Pair.of(Item.VALENTINE_SWORD, 1),
            Pair.of(Wing.VALENTINE_WINGS, 1)
        )), 5)
        .build();

    public HeartfeltCrate() {
        super(CrateType.HEARTFELT.name(), table);
    }

    @Override
    public Permission visibility() {
        return Permission.none();
    }

    @Override
    public Component name() {
        return Component.text("Heartfelt Crate")
            .color(Collection.VALENTINES_2023.display().color())
            .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.IRON_INGOT)
            .display(name())
            .modelData(Model.Crate.Valentine.FULL)
            .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.VALENTINES_2023;
    }

    @Override
    public ItemStack crateBase() {
        return ItemBuilder.of(Material.IRON_INGOT)
            .modelData(Model.Crate.Valentine.BASE)
            .build();
    }

    @Override
    public ItemStack crateLid() {
        return ItemBuilder.of(Material.IRON_INGOT)
            .modelData(Model.Crate.Valentine.LID)
            .build();
    }
}
