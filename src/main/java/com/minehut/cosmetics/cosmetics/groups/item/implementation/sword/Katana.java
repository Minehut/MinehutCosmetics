package com.minehut.cosmetics.cosmetics.groups.item.implementation.sword;

import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.groups.item.Item;
import com.minehut.cosmetics.cosmetics.groups.item.ItemCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.function.Supplier;

public class Katana extends ItemCosmetic {

    public static final Component NAME = Component.text("Katana's Katana")
            .color(NamedTextColor.GOLD)
            .decoration(TextDecoration.ITALIC, false);

    public static final Supplier<ItemStack> ITEM = ItemBuilder.of(Material.DIAMOND_SWORD)
            .display(NAME)
            .lore(
                    Component.empty(),
                    Component.text("A Katana forged from the finest metals").color(NamedTextColor.GOLD),
                    Component.text("to be held by the true 'Block Game Legend'").color(NamedTextColor.GOLD),
                    Component.empty()
            )
            .flags(ItemFlag.HIDE_ATTRIBUTES)
            .modelData(Model.Item.Sword.KATANA)
            .supplier();

    public Katana() {
        super(
                Item.KATANA.name(),
                NAME,
                Permission.uuid(UUID.fromString("0cab222b-63ce-46c0-ada3-a56365f2dc8a")),
                Permission.deny()
        );
    }

    @Override
    public ItemStack item() {
        return ITEM.get();
    }
}
