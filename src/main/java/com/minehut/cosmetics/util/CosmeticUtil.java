package com.minehut.cosmetics.util;

import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.util.data.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CosmeticUtil {

    public static void copyAttributes(@Nullable ItemStack fromItem, @Nullable ItemStack toItem) {
        if (fromItem == null || toItem == null) return;

        // copy any attributes from the item type
        for (final EquipmentSlot slot : EquipmentSlot.values()) {
            getBaseType(fromItem).getItemAttributes(slot).forEach((attribute, modifier) -> toItem.editMeta(meta -> {
                var attrs = meta.getAttributeModifiers(slot);
                attrs.put(attribute, modifier);
                meta.setAttributeModifiers(attrs);
            }));


            fromItem.getItemMeta().getAttributeModifiers(slot).forEach((attribute, modifier) -> toItem.editMeta(meta -> {
                var attrs = meta.getAttributeModifiers(slot);
                attrs.put(attribute, modifier);
                meta.setAttributeModifiers(attrs);
            }));
        }

        // copy enchants
        fromItem.getEnchantments().forEach(toItem::addUnsafeEnchantment);
    }

    /**
     * Checks it item has a material key in its data, if it doesn't just
     * return the item stack's type
     *
     * @param item to get data from
     * @return the type that represents this item stack
     */
    public static Material getBaseType(@NotNull ItemStack item) {
        return Optional.of(item)
                .map(ItemStack::getItemMeta)
                .flatMap(meta -> Key.MATERIAL.read(meta))
                .flatMap((material) -> EnumUtil.valueOfSafe(Material.class, material))
                .orElse(item.getType());
    }

    public static void swapType(ItemStack item, Material material) {
        item.editMeta(meta -> {
            final Material base = item.getType();

            if (!meta.hasDisplayName()) {
                meta.displayName(Component.translatable(base.translationKey()).decoration(TextDecoration.ITALIC, false));
            }
        });

        item.setType(material);
    }

    public static Optional<EquipmentSlot> getEquipmentSlot(@Nullable ItemStack item) {
        return Optional.ofNullable(item)
                .map(ItemStack::getItemMeta)
                .flatMap(Key.EQUIPMENT_SLOT::read)
                .flatMap(slot -> EnumUtil.valueOfSafe(EquipmentSlot.class, slot));
    }

    public static boolean isSkinned(@Nullable ItemStack item) {
        if (item == null) return false;
        return Key.SKINNED.read(item.getItemMeta()).isPresent();
    }

    public static Optional<Cosmetic> readCosmetic(@Nullable ItemStack stack) {
        if (stack == null) return Optional.empty();
        return Key.COSMETIC_CATEGORY.read(stack.getItemMeta()).flatMap(category ->
                Key.COSMETIC_ID.read(stack.getItemMeta()).flatMap(id ->
                        Cosmetic.fromCategoryId(category, id)
                )
        );
    }

    public static Optional<Cosmetic> readCosmetic(@Nullable PersistentDataHolder holder) {
        if (holder == null) return Optional.empty();
        return Key.COSMETIC_CATEGORY.read(holder).flatMap(category ->
                Key.COSMETIC_ID.read(holder).flatMap(id ->
                        Cosmetic.fromCategoryId(category, id)
                )
        );
    }

    public static void writeCosmeticKeys(@NotNull PersistentDataHolder holder, @NotNull Cosmetic cosmetic) {
        Key.COSMETIC_CATEGORY.write(holder, cosmetic.category().name());
        Key.COSMETIC_ID.write(holder, cosmetic.id());
    }

    public static void writeSkinKeys(@NotNull ItemStack item) {
        item.editMeta(meta -> Key.SKINNED.writeIfAbsent(meta, ""));
    }

    public static Integer getEquipmentSlotIndex(@NotNull EquipmentSlot slot) {
        switch (slot) {
            case FEET -> {
                return 8;
            }
            case LEGS -> {
                return 7;
            }
            case CHEST -> {
                return 6;
            }
            case HEAD -> {
                return 5;
            }
            default -> {
                return -1;
            }
        }
    }
}
