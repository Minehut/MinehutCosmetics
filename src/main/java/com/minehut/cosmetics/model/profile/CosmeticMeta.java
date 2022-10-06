package com.minehut.cosmetics.model.profile;

public class CosmeticMeta {
    private int quantity;

    public CosmeticMeta() {
    }

    public CosmeticMeta(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CosmeticMeta{" +
                "quantity=" + quantity +
                '}';
    }
}
