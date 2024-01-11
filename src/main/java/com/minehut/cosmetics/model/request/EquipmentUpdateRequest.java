package com.minehut.cosmetics.model.request;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.properties.CosmeticSlot;

public class EquipmentUpdateRequest {

    private final UUID uuid;
    private final Map<String, String> updates;

    public EquipmentUpdateRequest(UUID uuid, Map<CosmeticSlot, @Nullable Cosmetic> updates) {
        this.uuid = uuid;
        this.updates = new HashMap<>(updates.size());

        updates.forEach((slot, cosmetic) -> {
            this.updates.put(slot.name(), cosmetic == null ? "EMPTY" : cosmetic.getQualifiedId());
        });
    }

    public UUID getUuid() {
        return uuid;
    }

    public Map<String, String> getUpdates() {
        return updates;
    }
}
