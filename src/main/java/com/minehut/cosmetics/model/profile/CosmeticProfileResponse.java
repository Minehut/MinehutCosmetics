package com.minehut.cosmetics.model.profile;

import com.minehut.cosmetics.cosmetics.Cosmetic;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CosmeticProfileResponse {
    private Map<String, String> equipped = new HashMap<>();
    private CosmeticProfile profile = new CosmeticProfile();
    private String rank = "DEFAULT";
    private String packHash = "";

    public CosmeticProfileResponse() {
    }

    public CosmeticProfileResponse(Map<String, String> equipped, CosmeticProfile profile, String rank, String packHash) {
        this.equipped = equipped;
        this.profile = profile;
        this.rank = rank;
        this.packHash = packHash;
    }

    public Map<String, String> getEquipped() {
        return equipped;
    }

    public void setEquipped(Map<String, String> equipped) {
        this.equipped = equipped;
    }

    public CosmeticProfile getProfile() {
        return profile;
    }

    public void setProfile(CosmeticProfile profile) {
        this.profile = profile;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getQuantity(String category, String id) {
        return Optional.ofNullable(profile.getPurchased().get(category))
                .map(idMap -> idMap.get(id))
                .map(CosmeticData::getMeta)
                .map(CosmeticMeta::getQuantity)
                .orElse(0);
    }

    public int getQuantity(Cosmetic cosmetic) {
        return getQuantity(cosmetic.category().name(), cosmetic.id());
    }

    public int getGems() {
        final CosmeticProfile purchases = profile;
        final Map<String, CosmeticData> currencyMap = purchases.getPurchased().get("CURRENCY");
        if (currencyMap == null) return 0;
        final CosmeticData data = currencyMap.get("GEM");
        if (data == null) return 0;
        return data.getMeta().getQuantity();
    }

    public String getPackHash() {
        return packHash;
    }

    @Override
    public String toString() {
        return "CosmeticResponse{" +
                "equipped=" + equipped +
                ", profile=" + profile +
                ", rank='" + rank + '\'' +
                '}';
    }
}