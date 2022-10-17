package com.minehut.cosmetics.util.data;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record DataKey<T, Z>(NamespacedKey key, PersistentDataType<T, Z> type) {

    public Optional<Z> read(@Nullable PersistentDataHolder holder) {
        return Optional.ofNullable(holder).map(PersistentDataHolder::getPersistentDataContainer).map(data -> data.get(key, type));
    }

    public boolean has(PersistentDataHolder holder) {
        return read(holder).isPresent();
    }

    public void write(@NotNull PersistentDataHolder holder, Z value) {
        holder.getPersistentDataContainer().set(key, type, value);
    }

    public void remove(@NotNull PersistentDataHolder holder) {
        holder.getPersistentDataContainer().remove(key);
    }

    public void writeIfAbsent(@NotNull PersistentDataHolder holder, Z value) {
        if (has(holder)) return;
        write(holder, value);
    }
}