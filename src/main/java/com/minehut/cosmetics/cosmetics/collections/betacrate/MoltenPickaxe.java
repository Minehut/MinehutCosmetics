package com.minehut.cosmetics.cosmetics.collections.betacrate;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.Rarity;
import com.minehut.cosmetics.cosmetics.types.item.Item;
import com.minehut.cosmetics.cosmetics.types.item.ItemCosmetic;
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

public class MoltenPickaxe extends ItemCosmetic {
    public MoltenPickaxe() {
        super(Item.MOLTEN_PICKAXE.name());
    }

    @Override
    public Component name() {
        return Component.text("Molten Pickaxe")
                .color(NamedTextColor.GOLD)
                .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.DIAMOND_PICKAXE)
                .display(name())
                .flags(ItemFlag.HIDE_ATTRIBUTES)
                .modelData(Model.Item.Pickaxe.MOLTEN)
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.DRAGON_CRATE;
    }

    @Override
    public @NotNull Rarity rarity() {
        return Rarity.LEGENDARY;
    }
}
