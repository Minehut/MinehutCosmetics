package com.minehut.cosmetics.cosmetics.collections.netflix2022;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.companion.Companion;
import com.minehut.cosmetics.ui.model.Model;

import com.minehut.cosmetics.cosmetics.types.companion.CompanionCosmetic;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class WendellAndWildCompanion extends CompanionCosmetic {

    private static final Supplier<ItemStack> WENDELL = ItemBuilder.of(Material.SCUTE)
            .display(Component.text("Wendell").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false))
            .modelData(Model.Companion.WENDELL)
            .supplier();

    private static final Supplier<ItemStack> WILD = ItemBuilder.of(Material.SCUTE)
            .display(Component.text("Wild").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false))
            .modelData(Model.Companion.WILD)
            .supplier();

    private static final Supplier<ItemStack> WENDELL_AND_WILD = ItemBuilder.of(Material.SCUTE)
            .display(Component.text("Wendell & Wild").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false))
            .modelData(Model.Companion.WILD)
            .supplier();

    public WendellAndWildCompanion() {
        super(
                Companion.WENDELL_AND_WILD.name(),
                new Vector(0, -1.01, 0),
                true,
                true,
                false
        );
    }

    @Override
    public Permission visibility() {
        return Permission.deny();
    }

    @Override
    public Component name() {
        return Component.text("Wendell & Wild").color(rarity().display().color());
    }

    @Override
    public List<Function<Player, ItemStack>> getCompanionSuppliers() {
        return List.of(
                p -> WENDELL.get(),
                p -> WILD.get()
        );
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return WENDELL_AND_WILD.get();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.WENDELL_AND_WILD;
    }
}