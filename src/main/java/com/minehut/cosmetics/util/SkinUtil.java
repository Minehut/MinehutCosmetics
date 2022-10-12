package com.minehut.cosmetics.util;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.modules.KeyManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SkinUtil {

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
                .map(ItemMeta::getPersistentDataContainer)
                .map((data) -> data.get(Cosmetics.get().keyManager().MATERIAL, PersistentDataType.STRING))
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

    public static Optional<EquipmentSlot> getEquipmentSlot(ItemStack item) {
        return ItemUtil.readKeySafe(item, Cosmetics.get().keyManager().EQUIPMENT_SLOT, PersistentDataType.STRING)
                .flatMap(value -> EnumUtil.valueOfSafe(EquipmentSlot.class, value));
    }

    public static boolean isSkinned(ItemStack item) {
        return ItemUtil.readKeySafe(item, Cosmetics.get().keyManager().COSMETIC_ID, PersistentDataType.STRING).isPresent();
    }

    public static Optional<Cosmetic> getCosmetic(ItemStack stack) {
        final KeyManager keys = Cosmetics.get().keyManager();
        return ItemUtil.readKeySafe(stack, keys.COSMETIC_CATEGORY, PersistentDataType.STRING).flatMap(category ->
                ItemUtil.readKeySafe(stack, keys.COSMETIC_ID, PersistentDataType.STRING).flatMap(id ->
                        CosmeticCategory.getCosmetic(category, id)
                )
        );
    }

    public static void applyCosmeticKeys(@NotNull ItemStack item, @NotNull Cosmetic cosmetic) {
        final KeyManager keys = Cosmetics.get().keyManager();
        ItemUtil.editMeta(item, (ignored, data) -> {
            data.set(keys.COSMETIC_CATEGORY, PersistentDataType.STRING, cosmetic.category().name());
            data.set(keys.COSMETIC_ID, PersistentDataType.STRING, cosmetic.id());
        });
    }

    public static void removeSkinKeys(@NotNull ItemStack item) {
        final KeyManager keys = Cosmetics.get().keyManager();
        ItemUtil.editMeta(item, (ignored, data) -> {
            data.remove(keys.COSMETIC_ID);
            data.remove(keys.COSMETIC_CATEGORY);
        });
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
