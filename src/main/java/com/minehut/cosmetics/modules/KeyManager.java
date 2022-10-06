package com.minehut.cosmetics.modules;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

public class KeyManager {
    public NamespacedKey EQUIPMENT_SLOT;
    public NamespacedKey MATERIAL;

    // cosmetic category and id
    public NamespacedKey COSMETIC_CATEGORY;
    public NamespacedKey COSMETIC_ID;

    // identifier keys
    public NamespacedKey LEASHED;
    public NamespacedKey COSMETIC_ENTITY;


    // custom durability
    public NamespacedKey DURABILITY;
    public NamespacedKey MAX_DURABILITY;


    public KeyManager(Plugin plugin) {
        EQUIPMENT_SLOT = new NamespacedKey(plugin, "EQUIPMENT_SLOT");
        MATERIAL = new NamespacedKey(plugin, "MATERIAL");
        COSMETIC_CATEGORY = new NamespacedKey(plugin, "COSMETIC_CATEGORY");
        COSMETIC_ID = new NamespacedKey(plugin, "COSMETIC_ID");
        DURABILITY = new NamespacedKey(plugin, "DURABILITY");
        MAX_DURABILITY = new NamespacedKey(plugin, "MAX_DURABILITY");
        LEASHED = new NamespacedKey(plugin, "LEASHED");
        COSMETIC_ENTITY = new NamespacedKey(plugin, "COSMETIC_ENTITY");
    }

}
