package com.minehut.cosmetics.model.profile;

import java.util.HashMap;
import java.util.Map;

public class CosmeticProfile {
    private Map<String, Map<String, CosmeticData>> purchased = new HashMap<>();

    public CosmeticProfile(Map<String, Map<String, CosmeticData>> purchased) {
        this.purchased = purchased;
    }

    public CosmeticProfile() {
    }

    public Map<String, Map<String, CosmeticData>> getPurchased() {
        return purchased;
    }

    public void setPurchased(Map<String, Map<String, CosmeticData>> purchased) {
        this.purchased = purchased;
    }

    @Override
    public String toString() {
        return "CosmeticProfile{" +
                "purchased=" + purchased +
                '}';
    }
}
