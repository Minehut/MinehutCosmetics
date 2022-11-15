package com.minehut.cosmetics.model.request;

import com.minehut.cosmetics.model.profile.CosmeticData;

import java.util.UUID;

public class UnlockCosmeticRequest {
    private final UUID uuid;
    private final CosmeticData cosmetic;

    public UnlockCosmeticRequest(UUID uuid, CosmeticData cosmetic) {
        this.uuid = uuid;
        this.cosmetic = cosmetic;
    }
}
