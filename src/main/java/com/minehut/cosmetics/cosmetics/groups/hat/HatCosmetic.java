package com.minehut.cosmetics.cosmetics.groups.hat;

import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.properties.Equippable;
import com.minehut.cosmetics.cosmetics.properties.Skinnable;
import com.minehut.cosmetics.util.SkinUtil;
import com.minehut.cosmetics.util.data.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

public abstract class HatCosmetic extends Cosmetic implements Equippable, Skinnable {

    private boolean equipped = false;

    protected Supplier<ItemStack> itemSupplier;
    @Nullable
    private final ArmorStand entity;

    protected HatCosmetic(String id,
                          final Component name,
                          final Permission permission,
                          final Permission visibility,
                          Supplier<ItemStack> itemSupplier) {
        super(id, CosmeticCategory.HAT, name, permission, visibility);
        this.itemSupplier = itemSupplier;
        this.entity = null;
    }

    @Override
    public void equip() {
        if (equipped) return;
        equipped = true;
        player().ifPresent(player -> player.getInventory().setHelmet(menuIcon()));
    }

    @Override
    public void unequip() {
        if (!equipped) return;
        equipped = false;

        player().ifPresent(player -> player.getInventory().setHelmet(null));

    }

    private Optional<ArmorStand> entity() {
        return Optional.ofNullable(entity);
    }

    public ItemStack item() {
        return itemSupplier.get();
    }

    @Override
    public ItemStack menuIcon() {
        return item();
    }

    @Override
    public void applySkin(ItemStack item) {
        ItemStack base = item();

        // apply any keys we need to the item
        SkinUtil.copyAttributes(item, item);
        SkinUtil.writeSkinKeys(item);

        final Material type = item.getType();

        item.editMeta(meta -> {
            SkinUtil.writeCosmeticKeys(meta, this);

            Key.EQUIPMENT_SLOT.writeIfAbsent(meta, EquipmentSlot.HEAD.name());
            Key.MATERIAL.writeIfAbsent(meta, type.name());

            // durability keys
            if (meta instanceof Damageable damageable) {
                Key.DURABILITY.writeIfAbsent(meta, type.getMaxDurability() - damageable.getDamage());
                Key.MAX_DURABILITY.writeIfAbsent(meta, (int) type.getMaxDurability());
            }

            meta.setCustomModelData(base.getItemMeta().getCustomModelData());
        });

        SkinUtil.swapType(item, base.getType());
    }

    @Override
    public void removeSkin(ItemStack item) {
        item.setType(SkinUtil.getBaseType(item));
        item.editMeta(meta -> Key.SKINNED.remove(meta));
    }
}
