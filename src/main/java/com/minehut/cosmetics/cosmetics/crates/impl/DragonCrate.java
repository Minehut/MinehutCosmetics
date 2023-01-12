package com.minehut.cosmetics.cosmetics.crates.impl;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.crates.Crate;
import com.minehut.cosmetics.cosmetics.crates.CrateType;
import com.minehut.cosmetics.cosmetics.crates.WeightedTable;
import com.minehut.cosmetics.cosmetics.types.balloon.Balloon;
import com.minehut.cosmetics.cosmetics.types.companion.Companion;
import com.minehut.cosmetics.cosmetics.types.hat.Hat;
import com.minehut.cosmetics.cosmetics.types.item.Item;
import com.minehut.cosmetics.cosmetics.types.wing.Wing;
import com.minehut.cosmetics.util.ItemBuilder;
import com.minehut.cosmetics.util.structures.Pair;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DragonCrate extends Crate {

    private static final WeightedTable<Pair<CosmeticSupplier<? extends Cosmetic>, Integer>> table = new WeightedTable<>();

    static {
        // uncommon
        table.registerItem(Pair.of(Item.FISH_SWORD, 1), 10);
        table.registerItem(Pair.of(Item.FANCY_SHORT_SWORD, 1), 10);
        table.registerItem(Pair.of(Balloon.PAPER_LANTERN, 1), 10);
        table.registerItem(Pair.of(Hat.STEAMPUNK, 1), 10);

        // rare
        table.registerItem(Pair.of(Hat.GAMER_HEADSET, 1), 9);
        table.registerItem(Pair.of(Companion.UFO_COW, 1), 9);
        table.registerItem(Pair.of(Hat.TURTLE, 1), 9);
        table.registerItem(Pair.of(Wing.PEGASUS, 1), 8);

        // epic
        table.registerItem(Pair.of(Item.BAN_HAMMER, 1), 5);
        table.registerItem(Pair.of(Item.FANCY_FISHING_ROD, 1), 5);
        table.registerItem(Pair.of(Balloon.PAPER_DRAGON, 1), 5);
        table.registerItem(Pair.of(Wing.ARCADE, 1), 5);

        // legendary
        table.registerItem(Pair.of(Companion.DRAGON_EGG, 1), 2);
        table.registerItem(Pair.of(Hat.TECHNICAL_VISOR, 1), 1);
        table.registerItem(Pair.of(Item.MOLTEN_PICKAXE, 1), 1);
        table.registerItem(Pair.of(Wing.PEGASUS_LARGE, 1), 1);
    }

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
        return ItemBuilder.of(Material.CHEST)
                .display(name())
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.DRAGON_CRATE;
    }
}
