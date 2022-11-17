package com.minehut.cosmetics.model.request;

import java.util.UUID;

public record SalvageCosmeticRequest(UUID uuid, String category, String id, int quantity, int reward) { }
