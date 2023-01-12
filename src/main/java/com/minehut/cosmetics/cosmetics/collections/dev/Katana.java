package com.minehut.cosmetics.cosmetics.collections.dev;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
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

import java.util.UUID;

public class Katana extends ItemCosmetic {
    public Katana() {
        super(Item.KATANA.name());
    }

    @Override
    public Permission permission() {
        return Permission.uuid(UUID.fromString("0cab222b-63ce-46c0-ada3-a56365f2dc8a"));
    }

    @Override
    public Permission visibility() {
        return Permission.deny();
    }

    @Override
    public Component name() {
        return Component.text("Katana's Katana")
                .color(NamedTextColor.GOLD)
                .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.DIAMOND_SWORD)
                .display(name())
                .lore(
                        Component.empty(),
                        Component.text("A Katana forged from the finest metals").color(NamedTextColor.GOLD),
                        Component.text("to be held by the true 'Block Game Legend'").color(NamedTextColor.GOLD),
                        Component.empty()
                )
                .flags(ItemFlag.HIDE_ATTRIBUTES)
                .modelData(Model.Item.Sword.KATANA)
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.DEV;
    }
}
