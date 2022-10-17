package com.minehut.cosmetics.cosmetics.groups.wing.implementation;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticPermission;
import com.minehut.cosmetics.cosmetics.Model;
import com.minehut.cosmetics.cosmetics.groups.wing.Wing;
import com.minehut.cosmetics.cosmetics.groups.wing.WingCosmetic;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

public class BatWing extends WingCosmetic {

    private static final Component NAME = Component.text("Bat Wings")
            .color(NamedTextColor.GOLD)
            .decoration(TextDecoration.ITALIC, false);

    private static final Supplier<ItemStack> ITEM = ItemBuilder.of(Material.SCUTE)
            .display(NAME)
            .lore(
                    Component.empty(),
                    Component.text("Spooktacular 2022").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
                    Component.empty()
            )
            .modelData(Model.WING.BAT)
            .supplier();

    public BatWing() {
        super(
                Wing.BAT.name(),
                NAME,
                CosmeticPermission.hasPurchased(CosmeticCategory.WING.name(), Wing.BAT.name()),
                p -> ITEM.get()
        );
    }

    @Override
    public ItemStack menuIcon() {
        return ITEM.get();
    }
}