package com.minehut.cosmetics.cosmetics.collections.halloween2022;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.hat.HatCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class FoxEars extends HatCosmetic {
    public FoxEars() {
        super(com.minehut.cosmetics.cosmetics.types.hat.Hat.FOX_EARS.name());
    }

    @Override
    public Permission permission() {
        return Permission.hasPurchased(this);
    }

    @Override
    public Permission visibility() {
        return Permission.collectionIsActive(Collection.SPOOKY_22);
    }

    @Override
    public Component name() {
        return Component.text("Foxy Ears")
                .color(rarity().display().color())
                .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.DIAMOND_LEGGINGS)
                .display(name())
                .flags(ItemFlag.HIDE_ATTRIBUTES)
                .modelData(Model.Hat.FOX_EARS)
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.SPOOKY_22;
    }
}
