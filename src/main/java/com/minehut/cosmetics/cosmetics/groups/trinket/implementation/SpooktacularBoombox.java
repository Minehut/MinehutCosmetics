package com.minehut.cosmetics.cosmetics.groups.trinket.implementation;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.config.Mode;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.groups.equipment.CosmeticSlot;
import com.minehut.cosmetics.cosmetics.groups.trinket.TrinketCosmetic;
import com.minehut.cosmetics.cosmetics.properties.Equippable;
import com.minehut.cosmetics.cosmetics.properties.SlotHandler;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import com.minehut.cosmetics.util.SkinUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.function.Supplier;

public class SpooktacularBoombox extends TrinketCosmetic implements Equippable, SlotHandler {

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
            .modelData(Model.Trinket.BOOMBOX)
            .supplier();

    private CosmeticSlot slot = null;

    public SpooktacularBoombox() {
        super(
                com.minehut.cosmetics.cosmetics.groups.trinket.Trinket.SPOOKTACULAR_BOOMBOX.name(),
                DISPLAY_NAME,
                Permission.hasPurchased(CosmeticCategory.TRINKET.name(), com.minehut.cosmetics.cosmetics.groups.trinket.Trinket.SPOOKTACULAR_BOOMBOX.name()),
                Permission.deny()
        );
    }

    @Override
    public ItemStack menuIcon() {
        return ITEM.get();
    }

    @Override
    public void equip() {
        player().ifPresent(player -> slot().ifPresent(slot -> {

            final ItemStack item = ITEM.get();
            item.editMeta(meta -> SkinUtil.writeCosmeticKeys(meta, this));

            switch (Cosmetics.mode()) {
                // handle based on the slot being used
                case LOBBY -> {
                    switch (slot) {
                        case MAIN_HAND -> {
                            player.getInventory().setItem(6, item);
                            player.getInventory().setHeldItemSlot(6);
                        }
                        case OFF_HAND -> player.getInventory().setItemInOffHand(item);
                    }
                }
                case PLAYER_SERVER -> player.getInventory().addItem(item);
            }
        }));
    }

    @Override
    public void unequip() {
        player().ifPresent(player -> slot().ifPresent(slot -> {
            // handle like items in lobby mode
            if (Mode.LOBBY == Cosmetics.mode()) {
                switch (slot) {
                    case MAIN_HAND -> player.getInventory().setItem(6, null);
                    case OFF_HAND -> player.getInventory().setItemInOffHand(null);
                }
            }
        }));
    }

    @Override
    public Optional<CosmeticSlot> slot() {
        return Optional.ofNullable(slot);
    }

    @Override
    public void setSlot(CosmeticSlot slot) {
        this.slot = slot;
    }
}
