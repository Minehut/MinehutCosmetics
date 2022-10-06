package com.minehut.cosmetics.util;

import com.minehut.cosmetics.Cosmetics;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;

public class ItemUtil {


    public static <T, Z> void setIfUnset(PersistentDataContainer data, NamespacedKey key, PersistentDataType<T, Z> type, Z value) {
        if (data.has(key, type)) return;
        data.set(key, type, value);
    }

    public static void editMeta(@NotNull ItemStack stack, BiConsumer<ItemMeta, PersistentDataContainer> consumer) {
        stack.editMeta(meta -> {
            final PersistentDataContainer data = meta.getPersistentDataContainer();
            consumer.accept(meta, data);
        });
    }

    /**
     * Read a key from the given item and return an optional
     * containing the value if it exists.
     *
     * @param item to get read data from
     * @param key  to retrieve data for
     * @param type of data type stored
     * @param <T>  base object type
     * @param <Z>  converted object type
     * @return an optional containing a value of the read key or none
     */
    public static <T, Z> Optional<Z> readKeySafe(@Nullable ItemStack item, @NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type) {
        if (item == null || !item.hasItemMeta()) return Optional.empty();
        final PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
        return Optional.ofNullable(data.get(key, type));
    }
}
