package com.minehut.cosmetics.model.request;

import java.util.UUID;

public class EquipmentUpdateRequest {

    private final UUID uuid;
    private final CosmeticState state;

    public EquipmentUpdateRequest(UUID uuid, CosmeticState state) {
        this.uuid = uuid;
        this.state = state;
    }

    public UUID getUuid() {
        return uuid;
    }

    public CosmeticState getState() {
        return state;
    }
}
