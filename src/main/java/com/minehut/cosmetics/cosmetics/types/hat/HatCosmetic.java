package com.minehut.cosmetics.cosmetics.types.hat;

import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.properties.Equippable;
import com.minehut.cosmetics.cosmetics.properties.Skinnable;
import com.minehut.cosmetics.util.CosmeticUtil;
import com.minehut.cosmetics.util.data.Key;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class HatCosmetic extends Cosmetic implements Equippable, Skinnable {

    private boolean equipped = false;
    @Nullable
    private final ArmorStand entity;

    protected HatCosmetic(String id) {
        super(id, CosmeticCategory.HAT);
        this.entity = null;
    }

    @Override
    public void equip() {
        if (equipped) {
            return;
        }
        equipped = true;
        player().ifPresent(player -> player.getInventory().setHelmet(menuIcon()));
    }

    @Override
    public void unequip() {
        if (!equipped){
            return;
        }
        equipped = false;

        player().ifPresent(player -> player.getInventory().setHelmet(null));

    }

    public ItemStack item() {
        return menuIcon();
    }

    @Override
    public abstract @NotNull ItemStack menuIcon();

    @Override
    public void applySkin(ItemStack item) {
        ItemStack base = item();

        // apply any keys we need to the item
        CosmeticUtil.copyAttributes(item, item);
        CosmeticUtil.writeSkinKeys(item);

        final Material type = item.getType();

        item.editMeta(meta -> {
            CosmeticUtil.writeCosmeticKeys(meta, this);

            Key.EQUIPMENT_SLOT.writeIfAbsent(meta, EquipmentSlot.HEAD.name());
            Key.MATERIAL.writeIfAbsent(meta, type.name());

            // durability keys
            if (meta instanceof Damageable damageable) {
                Key.DURABILITY.writeIfAbsent(meta, type.getMaxDurability() - damageable.getDamage());
                Key.MAX_DURABILITY.writeIfAbsent(meta, (int) type.getMaxDurability());
            }

            meta.setCustomModelData(base.getItemMeta().getCustomModelData());
        });

        CosmeticUtil.swapType(item, base.getType());
    }

    @Override
    public void removeSkin(ItemStack item) {
        item.setType(CosmeticUtil.getBaseType(item));
        item.editMeta(meta -> Key.SKINNED.remove(meta));
    }
}
