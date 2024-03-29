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

public class DragonCrate extends Crate {

    private static final WeightedTable<CrateEntry> table = WeightedTable.<CrateEntry>builder()
        .register(CrateEntry.of(Rarity.UNCOMMON, List.of(
            Pair.of(Item.FISH_SWORD, 1),
            Pair.of(Item.FANCY_SHORT_SWORD, 1),
            Pair.of(Balloon.PAPER_LANTERN, 1),
            Pair.of(Hat.STEAMPUNK, 1))
        ), 40)
        .register(CrateEntry.of(Rarity.RARE, List.of(
            Pair.of(Hat.GAMER_HEADSET, 1),
            Pair.of(Companion.UFO_COW, 1),
            Pair.of(Hat.TURTLE, 1),
            Pair.of(Wing.PEGASUS, 1))
        ), 35)
        .register(CrateEntry.of(Rarity.EPIC, List.of(
            Pair.of(Item.BAN_HAMMER, 1),
            Pair.of(Item.FANCY_FISHING_ROD, 1),
            Pair.of(Balloon.PAPER_DRAGON, 1),
            Pair.of(Wing.ARCADE, 1))
        ), 20)
        .register(CrateEntry.of(Rarity.LEGENDARY, List.of(
            Pair.of(Companion.DRAGON_EGG, 1),
            Pair.of(Hat.TECHNICAL_VISOR, 1),
            Pair.of(Item.MOLTEN_PICKAXE, 1),
            Pair.of(Wing.PEGASUS_LARGE, 1)
        )), 5)
        .build();

    public DragonCrate() {
        super(CrateType.DRAGON_CRATE.name(), table);
    }

    @Override
    public Permission visibility() {
        return Permission.none();
    }

    @Override
    public Component name() {
        return Component.text("Minehut Dragon Crate")
            .color(rarity().display().color())
            .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.IRON_INGOT)
            .display(name())
            .modelData(Model.Crate.Dragon.FULL)
            .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.DRAGON_CRATE;
    }

    @Override
    public ItemStack crateBase() {
        return ItemBuilder.of(Material.IRON_INGOT)
            .modelData(Model.Crate.Dragon.BASE)
            .build();
    }

    @Override
    public ItemStack crateLid() {
        return ItemBuilder.of(Material.IRON_INGOT)
            .modelData(Model.Crate.Dragon.LID)
            .build();
    }
}
