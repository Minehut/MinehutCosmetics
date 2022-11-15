package com.minehut.cosmetics.model.profile;

import java.util.UUID;

public class ConsumeResponse {
    private final UUID uuid;
    private final String category;
    private final String id;
    private final int quantity;

    public ConsumeResponse(UUID uuid, String category, String id, int quantity) {
        this.uuid = uuid;
        this.category = category;
        this.id = id;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }
}