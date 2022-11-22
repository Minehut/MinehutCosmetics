package com.minehut.cosmetics.cosmetics.types.item;

import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.properties.CosmeticSlot;
import com.minehut.cosmetics.cosmetics.properties.Equippable;
import com.minehut.cosmetics.cosmetics.properties.Skinnable;
import com.minehut.cosmetics.cosmetics.properties.SlotHandler;
import com.minehut.cosmetics.util.ItemBuilder;
import com.minehut.cosmetics.util.SkinUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class ItemCosmetic extends Cosmetic implements Equippable, Skinnable, SlotHandler {
    private boolean equipped = false;

    private @Nullable CosmeticSlot slot = null;

    protected ItemCosmetic(String id) {
        super(id, CosmeticCategory.ITEM);
    }

    @Override
    public void equip() {
        if (equipped) return;
        equipped = true;
        player().ifPresent(player -> slot().ifPresent(slot -> {

            switch (slot) {
                case MAIN_HAND -> {
                    player.getInventory().setItem(6, item());
                    player.getInventory().setHeldItemSlot(6);
                }
                case OFF_HAND -> player.getInventory().setItemInOffHand(item());
            }
        }));
    }

    @Override
    public void unequip() {
        if (!equipped) return;
        equipped = false;

        player().ifPresent(player -> slot().ifPresent(slot -> {
            switch (slot) {
                case MAIN_HAND -> player.getInventory().setItem(6, null);
                case OFF_HAND -> player.getInventory().setItemInOffHand(null);
            }
        }));
    }

    public ItemStack item() {
        return menuIcon();
    }

    @Override
    public void applySkin(ItemStack item) {
        SkinUtil.writeSkinKeys(item);
        ItemStack base = item();
        item.editMeta(meta -> {
            meta.setCustomModelData(base.getItemMeta().getCustomModelData());
            SkinUtil.writeCosmeticKeys(meta, this);
        });
    }

    @Override
    public Optional<CosmeticSlot> slot() {
        return Optional.ofNullable(slot);
    }

    @Override
    public void setSlot(@Nullable CosmeticSlot slot) {
        this.slot = slot;
    }
}
