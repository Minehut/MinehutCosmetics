package com.minehut.cosmetics.cosmetics.collections.general;


import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.companion.CompanionCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class BearCompanion extends CompanionCosmetic {
    public BearCompanion() {
        super(
                com.minehut.cosmetics.cosmetics.types.companion.Companion.BEAR.name(),
                new Vector(0, -1, 0),
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
        return Permission.collectionIsActive(Collection.GENERAL);
    }

    @Override
    public Component name() {
        return Component.text("Teddy")
                .decoration(TextDecoration.ITALIC, false)
                .color(NamedTextColor.GOLD);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.SCUTE)
                .display(name())
                .modelData(Model.Companion.BEAR)
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.GENERAL;
    }
}
