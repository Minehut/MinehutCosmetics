package com.minehut.cosmetics.model.request;

import java.util.UUID;

public record ModifyCosmeticQuantityRequest(UUID uuid, String category, String id, int quantity) {
}
