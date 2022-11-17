package com.minehut.cosmetics.model.request;

import com.minehut.cosmetics.model.profile.CosmeticData;

import java.util.UUID;

public record UnlockCosmeticRequest(UUID uuid, CosmeticData cosmetic) {}
