package com.minehut.cosmetics.model.request;

import java.util.UUID;

public record EquipCosmeticRequest(UUID uuid, String slot, String id) {}
