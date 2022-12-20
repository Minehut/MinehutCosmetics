package com.minehut.cosmetics.cosmetics.types.trinket;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.config.Mode;
import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.properties.CosmeticSlot;
import com.minehut.cosmetics.cosmetics.types.trinket.TrinketCosmetic;
import com.minehut.cosmetics.cosmetics.properties.Equippable;
import com.minehut.cosmetics.cosmetics.properties.SlotHandler;
import com.minehut.cosmetics.cosmetics.types.trinket.Trinket;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import com.minehut.cosmetics.util.SkinUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Supplier;

public abstract class ItemTrinket extends TrinketCosmetic implements Equippable, SlotHandler {
    private CosmeticSlot slot = null;

    public ItemTrinket(String name) {
        super(name);
    }

    @Override
    public void equip() {
        player().ifPresent(player -> slot().ifPresent(slot -> {

            final ItemStack item = menuIcon();
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
