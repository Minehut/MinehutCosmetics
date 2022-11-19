package com.minehut.cosmetics.cosmetics.collections.netflix2022;

import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.ui.model.Model;

import com.minehut.cosmetics.cosmetics.types.companion.CompanionCosmetic;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.function.Supplier;

public class WendellAndWildCompanion extends CompanionCosmetic {

    private static final Supplier<ItemStack> WENDELL = ItemBuilder.of(Material.SCUTE)
            .display(Component.text("Wendell").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false))
            .lore(
                    Component.empty(),
                    Component.text("From the Netflix Original Film Wendell & Wild").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
                    Component.empty()
            )
            .modelData(Model.Companion.WENDELL)
            .supplier();

    private static final Supplier<ItemStack> WILD = ItemBuilder.of(Material.SCUTE)
            .display(Component.text("Wild").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false))
            .lore(
                    Component.empty(),
                    Component.text("From the Netflix Original Film Wendell & Wild").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
                    Component.empty()
            )
            .modelData(Model.Companion.WILD)
            .supplier();

    private static final Supplier<ItemStack> WENDELL_AND_WILD = ItemBuilder.of(Material.SCUTE)
            .display(Component.text("Wendell & Wild").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false))
            .lore(
                    Component.empty(),
                    Component.text("From the Netflix Original Film Wendell & Wild").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
                    Component.empty()
            )
            .modelData(Model.Companion.WILD)
            .supplier();

    public WendellAndWildCompanion() {
        super(
                com.minehut.cosmetics.cosmetics.types.companion.Companion.WENDELL_AND_WILD.name(),
                Component.text("Wendell & Wild").color(NamedTextColor.GOLD),
                List.of(
                        player -> WENDELL.get(),
                        player -> WILD.get()
                ),
                new Vector(0, -1.01, 0),
                true,
                true,
                false
        );
    }

    @Override
    public Permission permission() {
        return Permission.hasPurchased(this);
    }

    @Override
    public Permission visibility() {
        return Permission.deny();
    }

    @Override
    public ItemStack menuIcon() {
        return WENDELL_AND_WILD.get();
    }
}