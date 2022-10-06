package com.minehut.cosmetics.model.profile;

import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@SerializableAs("CosmeticProfile")
public class LocalCosmeticProfile implements ConfigurationSerializable {
    private Map<String, String> equipped;

    public LocalCosmeticProfile(Map<String, String> equipped) {
        this.equipped = equipped;
    }

    public LocalCosmeticProfile() {
        this(new HashMap<>());
    }

    public Map<String, String> getEquipped() {
        return equipped;
    }

    public void setEquipped(Map<String, String> equipped) {
        this.equipped = equipped;
    }

    public static LocalCosmeticProfile deserialize(Map<String, Object> map) {
        try {
            final Map<String, String> equipped = (Map<String, String>) map.getOrDefault("equipped", new HashMap<>());
            return new LocalCosmeticProfile(equipped);
        } catch (Exception ignored) {
            return new LocalCosmeticProfile();
        }
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        return Map.of(
                "equipped", equipped
        );
    }

    public static LocalCosmeticProfile from(Map<CosmeticCategory, Cosmetic> profile) {
        final LocalCosmeticProfile local = new LocalCosmeticProfile();
        profile.forEach(((category, cosmetic) -> local.equipped.put(category.name(), cosmetic.id())));
        return local;
    }
}
