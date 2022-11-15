package com.minehut.cosmetics.crates.impl;

import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.groups.hat.Hat;
import com.minehut.cosmetics.cosmetics.groups.item.Item;
import com.minehut.cosmetics.crates.Crate;
import com.minehut.cosmetics.crates.CrateType;
import com.minehut.cosmetics.crates.WeightedTable;
import com.minehut.cosmetics.util.ItemBuilder;
import com.minehut.cosmetics.util.structures.Pair;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class TestCrate extends Crate {

    private static final Component NAME = Component.text("Test Chest").color(NamedTextColor.RED);

    private static final Supplier<ItemStack> ICON = ItemBuilder.of(Material.CHEST)
            .display(NAME)
            .supplier();

    private static final WeightedTable<Pair<CosmeticSupplier<? extends Cosmetic>, Integer>> table = new WeightedTable<>();

    static {
        table.registerItem(Pair.of(Hat.CAT_EARS, 1), 10);
        table.registerItem(Pair.of(Hat.DEVIL_HORNS, 1), 10);
        table.registerItem(Pair.of(Hat.MAID, 1), 10);
        table.registerItem(Pair.of(Item.FALL_22_LEAF_SWORD, 1), 2);
    }

    public TestCrate() {
        super(CrateType.TEST.name(), NAME, table);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ICON.get();
    }
}
