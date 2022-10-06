package com.minehut.cosmetics.util;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class EnumUtil {

    public static <T extends Enum<T>> Optional<T> valueOfSafe(Class<T> clazz, @Nullable String query) {
        if (query == null) return Optional.empty();
        try {
            return Optional.of(Enum.valueOf(clazz, query));
        } catch (Exception ignored) {
            return Optional.empty();
        }
    }
}