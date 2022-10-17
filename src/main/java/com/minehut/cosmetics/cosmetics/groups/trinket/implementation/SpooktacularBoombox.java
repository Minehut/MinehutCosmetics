package com.minehut.cosmetics.cosmetics.groups.trinket.implementation;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticPermission;
import com.minehut.cosmetics.cosmetics.Model;
import com.minehut.cosmetics.cosmetics.groups.trinket.Trinket;
import com.minehut.cosmetics.cosmetics.groups.trinket.TrinketCosmetic;
import com.minehut.cosmetics.cosmetics.properties.Equippable;
import com.minehut.cosmetics.util.ItemBuilder;
import com.minehut.cosmetics.util.SkinUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public class SpooktacularBoombox extends TrinketCosmetic implements Equippable {

    private static final Component DISPLAY_NAME = Component.text("Kat's Boombox")
            .color(NamedTextColor.GOLD)
            .decoration(TextDecoration.ITALIC, false);
    private static final Supplier<ItemStack> ITEM = ItemBuilder.of(Material.SCUTE)
            .display(DISPLAY_NAME)
            .lore(
                    Component.empty(),
                    Component.text("From the Netflix Original Film Wendell & Wild").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
                    Component.empty()
            )
            .modelData(Model.Item.TRINKET.BOOMBOX)
            .supplier();

    public SpooktacularBoombox() {
        super(
                Trinket.SPOOKTACULAR_BOOMBOX.name(),
                DISPLAY_NAME,
                CosmeticPermission.hasPurchased(CosmeticCategory.TRINKET.name(), Trinket.SPOOKTACULAR_BOOMBOX.name())
        );
    }

    @Override
    public ItemStack menuIcon() {
        return ITEM.get();
    }

    @Override
    public void equip() {
        player().ifPresent(player -> {
            final ItemStack item = ITEM.get();
            item.editMeta(meta -> SkinUtil.writeCosmeticKeys(meta, this));
            player.getInventory().addItem(item);
        });
    }

    @Override
    public void unequip() {
        // we don't unequip trinkets, we just give them to the player
    }
}
