package com.minehut.cosmetics.util;

public class Validate {

    public static void isTrue(boolean condition, String message) {
        if (condition) return;
        throw new IllegalArgumentException(message);
    }
}
