package com.minehut.cosmetics.model.request;

import java.util.Map;

import com.google.gson.annotations.SerializedName;
import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.properties.CosmeticSlot;

public class CosmeticState {

    @SerializedName("HEAD")
    private String head = "EMPTY";
    @SerializedName("BACK")
    private String back = "EMPTY";
    @SerializedName("MAIN_HAND")
    private String mainHand = "EMPTY";
    @SerializedName("OFF_HAND")
    private String offHand = "EMPTY";
    @SerializedName("BALLOON")
    private String balloon = "EMPTY";
    @SerializedName("COMPANION")
    private String companion = "EMPTY";
    @SerializedName("PARTICLE")
    private String particle = "EMPTY";

    public CosmeticState(String head, String back, String mainHand, String offHand, String balloon, String companion,
            String particle) {
        this.head = head;
        this.back = back;
        this.mainHand = mainHand;
        this.offHand = offHand;
        this.balloon = balloon;
        this.companion = companion;
        this.particle = particle;
    }

    public CosmeticState() {
    }

    public static CosmeticState fromMap(Map<CosmeticSlot, Cosmetic> map) {

        CosmeticState state = new CosmeticState();
        for (CosmeticSlot slot : CosmeticSlot.values()) {
            Cosmetic cosmetic = map.get(slot);
            String id = cosmetic == null ? "EMPTY" : cosmetic.getQualifiedId();
            switch (slot) {
                case HEAD -> state.setHead(id);
                case BACK -> state.setBack(id);
                case MAIN_HAND -> state.setMainHand(id);
                case OFF_HAND -> state.setOffHand(id);
                case BALLOON -> state.setBalloon(id);
                case COMPANION -> state.setCompanion(id);
                case PARTICLE -> state.setParticle(id);
                default ->
                    Cosmetics.get().getLogger().warning("Unknown cosmetic slot %s detected.".formatted(slot.name()));
            }
        }

        return state;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public String getMainHand() {
        return mainHand;
    }

    public void setMainHand(String mainHand) {
        this.mainHand = mainHand;
    }

    public String getOffHand() {
        return offHand;
    }

    public void setOffHand(String offHand) {
        this.offHand = offHand;
    }

    public String getBalloon() {
        return balloon;
    }

    public void setBalloon(String balloon) {
        this.balloon = balloon;
    }

    public String getCompanion() {
        return companion;
    }

    public void setCompanion(String companion) {
        this.companion = companion;
    }

    public String getParticle() {
        return particle;
    }

    public void setParticle(String particle) {
        this.particle = particle;
    }
}
