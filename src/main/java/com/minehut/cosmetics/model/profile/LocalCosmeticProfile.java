package com.minehut.cosmetics.model.profile;

import com.minehut.cosmetics.model.request.CosmeticState;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@SerializableAs("CosmeticProfile")
public class LocalCosmeticProfile implements ConfigurationSerializable {
    private CosmeticState equipped;

    public LocalCosmeticProfile(CosmeticState equipped) {
        this.equipped = equipped;
    }

    public LocalCosmeticProfile() {
        this(new CosmeticState());
    }

    public CosmeticState getEquipped() {
        return equipped;
    }

    public void setEquipped(CosmeticState equipped) {
        this.equipped = equipped;
    }

    public static LocalCosmeticProfile deserialize(Map<String, Object> map) {
        try {
            final CosmeticState equipped = (CosmeticState) map.getOrDefault("equipped", new HashMap<>());
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

    public static LocalCosmeticProfile from(CosmeticState state) {
        return new LocalCosmeticProfile(state);
    }
}
