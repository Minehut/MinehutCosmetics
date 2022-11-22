package com.minehut.cosmetics.cosmetics.collections.halloween2022;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.util.ItemBuilder;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.hat.HatCosmetic;
import com.minehut.cosmetics.ui.model.Model;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class CatEars extends HatCosmetic {

    private static final Component NAME = Component.text("Cat Ears")
            .color(NamedTextColor.GOLD)
            .decoration(TextDecoration.ITALIC, false);
    private static final Supplier<ItemStack> ITEM = ItemBuilder.of(Material.DIAMOND_LEGGINGS)
            .display(NAME)
            .lore(
                    Component.empty(),
                    Collection.SPOOKY_22.tag(),
                    Component.empty()
            )
            .flags(ItemFlag.HIDE_ATTRIBUTES)
            .modelData(Model.Hat.CAT_EARS)
            .supplier();


    public CatEars() {
        super(com.minehut.cosmetics.cosmetics.types.hat.Hat.CAT_EARS.name());
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
        return NAME;
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ITEM.get();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.SPOOKY_22;
    }
}
