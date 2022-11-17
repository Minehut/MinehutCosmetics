package com.minehut.cosmetics.util.data;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class Key {

    public static DataKey<String, String> EQUIPMENT_SLOT;
    public static DataKey<String, String> MATERIAL;

    // cosmetic slot and id
    public static DataKey<String, String> COSMETIC_CATEGORY;
    public static DataKey<String, String> COSMETIC_ID;

    // identifier keys
    public static DataKey<String, String> LEASHED;
    public static DataKey<String, String> COSMETIC_ENTITY;
    public static DataKey<String, String> SKINNED;


    // custom durability
    public static DataKey<Integer, Integer> DURABILITY;
    public static DataKey<Integer, Integer> MAX_DURABILITY;

    public static void init(Plugin plugin) {
        // vanilla data keys
        EQUIPMENT_SLOT = new DataKey<>(new NamespacedKey(plugin, "EQUIPMENT_SLOT"), PersistentDataType.STRING);
        MATERIAL = new DataKey<>(new NamespacedKey(plugin, "MATERIAL"), PersistentDataType.STRING);

        // cosmetic info keys
        COSMETIC_CATEGORY = new DataKey<>(new NamespacedKey(plugin, "COSMETIC_CATEGORY"), PersistentDataType.STRING);
        COSMETIC_ID = new DataKey<>(new NamespacedKey(plugin, "COSMETIC_ID"), PersistentDataType.STRING);
        SKINNED = new DataKey<>(new NamespacedKey(plugin, "SKINNED"), PersistentDataType.STRING);

        // durability management
        DURABILITY = new DataKey<>(new NamespacedKey(plugin, "DURABILITY"), PersistentDataType.INTEGER);
        MAX_DURABILITY = new DataKey(new NamespacedKey(plugin, "MAX_DURABILITY"), PersistentDataType.INTEGER);

        // entity keys
        LEASHED = new DataKey<>(new NamespacedKey(plugin, "LEASHED"), PersistentDataType.STRING);
        COSMETIC_ENTITY = new DataKey<>(new NamespacedKey(plugin, "COSMETIC_ENTITY"), PersistentDataType.STRING);
    }
}