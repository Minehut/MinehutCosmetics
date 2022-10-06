package com.minehut.cosmetics.cosmetics.groups.hat;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.properties.Equippable;
import com.minehut.cosmetics.cosmetics.properties.Skinnable;
import com.minehut.cosmetics.modules.KeyManager;
import com.minehut.cosmetics.util.ItemUtil;
import com.minehut.cosmetics.util.SkinUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class HatCosmetic extends Cosmetic implements Equippable, Skinnable {

    private boolean equipped = false;

    protected Supplier<ItemStack> itemSupplier;
    @Nullable
    private final ArmorStand entity;

    protected HatCosmetic(String id, final Component name, final Function<Player, CompletableFuture<Boolean>> permission, Supplier<ItemStack> itemSupplier) {
        super(id, name, permission, CosmeticCategory.HAT);
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
        SkinUtil.copyAttributes(item, item);
        SkinUtil.applyCosmeticKeys(item, this);

        ItemUtil.editMeta(item, (meta, data) -> {
            KeyManager keys = Cosmetics.get().keyManager();
            meta.setCustomModelData(base.getItemMeta().getCustomModelData());

            // set equipment slot key
            ItemUtil.setIfUnset(data, keys.EQUIPMENT_SLOT, PersistentDataType.STRING, EquipmentSlot.HEAD.name());
            // set material key
            final Material type = item.getType();
            ItemUtil.setIfUnset(data, keys.MATERIAL, PersistentDataType.STRING, type.name());

            // durability keys
            if (meta instanceof Damageable damageable) {
                ItemUtil.setIfUnset(data, keys.DURABILITY, PersistentDataType.INTEGER, type.getMaxDurability() - damageable.getDamage());
                ItemUtil.setIfUnset(data, keys.MAX_DURABILITY, PersistentDataType.INTEGER, (int) type.getMaxDurability());
            }
        });

        SkinUtil.swapType(item, base.getType());
    }

    @Override
    public void removeSkin(ItemStack item) {
        if (!item.hasItemMeta()) return;
        item.setType(SkinUtil.getBaseType(item));
    }
}
