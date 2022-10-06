package com.minehut.cosmetics.model.profile;

import java.util.HashMap;
import java.util.Map;

public class CosmeticProfileResponse {
    private Map<String, String> equipped = new HashMap<>();
    private CosmeticProfile profile = new CosmeticProfile();
    private String rank = "DEFAULT";

    public CosmeticProfileResponse() {
    }

    public CosmeticProfileResponse(Map<String, String> equipped, CosmeticProfile profile, String rank) {
        this.equipped = equipped;
        this.profile = profile;
        this.rank = rank;
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

    @Override
    public String toString() {
        return "CosmeticResponse{" +
                "equipped=" + equipped +
                ", profile=" + profile +
                ", rank='" + rank + '\'' +
                '}';
    }
}