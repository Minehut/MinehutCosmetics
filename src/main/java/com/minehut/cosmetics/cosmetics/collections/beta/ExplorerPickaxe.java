package com.minehut.cosmetics.cosmetics.collections.beta;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Rarity;
import com.minehut.cosmetics.cosmetics.types.item.Item;
import com.minehut.cosmetics.cosmetics.types.item.ItemCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ExplorerPickaxe extends ItemCosmetic {

    public ExplorerPickaxe() {
        super(Item.EXPLORER_PICKAXE.name());
    }

    @Override
    public Component name() {
        return Component.text("Explorer’s Pickaxe")
                .color(rarity().display().color())
                .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.DIAMOND_PICKAXE)
                .display(name())
                .lore(
                        Component.empty(),
                        Collection.BETA.display(),
                        Component.empty()
                )
                .flags(ItemFlag.HIDE_ATTRIBUTES)
                .modelData(Model.Item.Pickaxe.EXPLORER)
                .build();
    }

    @Override
    public @NotNull Rarity rarity() {
        return Rarity.RARE;
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.BETA;
    }
}
