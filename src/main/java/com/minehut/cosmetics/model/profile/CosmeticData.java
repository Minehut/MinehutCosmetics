package com.minehut.cosmetics.model.profile;

public class CosmeticData {
    private String category;
    private String id;
    private final CosmeticMeta meta;

    public CosmeticData(String category, String id, CosmeticMeta meta) {
        this.category = category;
        this.id = id;
        this.meta = meta;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CosmeticMeta getMeta() {
        return meta;
    }

    @Override
    public String toString() {
        return "CosmeticData{" +
            "slot='" + category + '\'' +
            ", id='" + id + '\'' +
            ", meta=" + meta +
            '}';
    }
}
