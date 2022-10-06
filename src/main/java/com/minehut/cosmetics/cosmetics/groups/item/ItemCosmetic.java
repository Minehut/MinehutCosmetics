package com.minehut.cosmetics.cosmetics.groups.item;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.properties.Equippable;
import com.minehut.cosmetics.cosmetics.properties.Skinnable;
import com.minehut.cosmetics.modules.KeyManager;
import com.minehut.cosmetics.util.SkinUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class ItemCosmetic extends Cosmetic implements Equippable, Skinnable {
    protected Supplier<ItemStack> itemSupplier;
    private boolean equipped = false;

    protected ItemCosmetic(String id, final Component name, final Function<Player, CompletableFuture<Boolean>> permission, Supplier<ItemStack> itemSupplier) {
        super(id, name, permission, CosmeticCategory.ITEM);
        this.itemSupplier = itemSupplier;
    }

    @Override
    public void equip() {
        if (equipped) return;
        equipped = true;
        player().ifPresent(player -> {
            player.getInventory().setItem(6, item());
            player.getInventory().setHeldItemSlot(6);
        });
    }

    @Override
    public void unequip() {
        if (!equipped) return;
        equipped = false;
        player().ifPresent(player -> player.getInventory().setItem(6, null));
    }

    public ItemStack item() {
        return itemSupplier.get();
    }

    @Override
    public ItemStack menuIcon() {
        return item();
    }

    public Supplier<ItemStack> getItemSupplier() {
        return itemSupplier;
    }

    @Override
    public void applySkin(ItemStack item) {
        SkinUtil.applyCosmeticKeys(item, this);

        ItemStack base = item();
        item.editMeta(meta -> {
            // apply any visual things
            meta.setCustomModelData(base.getItemMeta().getCustomModelData());
        });
    }

    @Override
    public void removeSkin(ItemStack item) {
        Skinnable.super.removeSkin(item);
    }
}
