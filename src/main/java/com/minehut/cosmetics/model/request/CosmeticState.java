package com.minehut.cosmetics.model.request;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.gson.annotations.SerializedName;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.properties.CosmeticSlot;
import com.minehut.cosmetics.cosmetics.properties.Equippable;

public class CosmeticState implements ConfigurationSerializable {

    @SerializedName("HEAD")
    private Cosmetic head = null;
    @SerializedName("BACK")
    private Cosmetic back = null;
    @SerializedName("MAIN_HAND")
    private Cosmetic mainHand = null;
    @SerializedName("OFF_HAND")
    private Cosmetic offHand = null;
    @SerializedName("BALLOON")
    private Cosmetic balloon = null;
    @SerializedName("COMPANION")
    private Cosmetic companion = null;
    @SerializedName("PARTICLE")
    private Cosmetic particle = null;

    public static CosmeticState fromMap(Map<CosmeticSlot, Cosmetic> map) {

        CosmeticState state = new CosmeticState();
        for (CosmeticSlot slot : CosmeticSlot.values()) {
            state.set(slot, map.get(slot));
        }
        return state;
    }

    public void set(CosmeticSlot slot, Cosmetic cosmetic) {
        // handle unequipping the old cosmetic
        Cosmetic old = get(slot);
        if (old instanceof Equippable equippable) {
            equippable.unequip();
        }

        switch (slot) {
            case HEAD -> setHead(cosmetic);
            case BACK -> setBack(cosmetic);
            case MAIN_HAND -> setMainHand(cosmetic);
            case OFF_HAND -> setOffHand(cosmetic);
            case BALLOON -> setBalloon(cosmetic);
            case COMPANION -> setCompanion(cosmetic);
            case PARTICLE -> setParticle(cosmetic);
        }
    }

    public void remove(CosmeticSlot slot) {
        set(slot, null);
    }

    public @Nullable Cosmetic get(CosmeticSlot slot) {
        return switch (slot) {
            case HEAD -> getHead();
            case BACK -> getBack();
            case MAIN_HAND -> getMainHand();
            case OFF_HAND -> getOffHand();
            case BALLOON -> getBalloon();
            case COMPANION -> getCompanion();
            case PARTICLE -> getParticle();
        };
    }

    public Cosmetic getHead() {
        return head;
    }

    public void setHead(Cosmetic head) {
        this.head = head;
    }

    public Cosmetic getBack() {
        return back;
    }

    public void setBack(Cosmetic back) {
        this.back = back;
    }

    public Cosmetic getMainHand() {
        return mainHand;
    }

    public void setMainHand(Cosmetic mainHand) {
        this.mainHand = mainHand;
    }

    public Cosmetic getOffHand() {
        return offHand;
    }

    public void setOffHand(Cosmetic offHand) {
        this.offHand = offHand;
    }

    public Cosmetic getBalloon() {
        return balloon;
    }

    public void setBalloon(Cosmetic balloon) {
        this.balloon = balloon;
    }

    public Cosmetic getCompanion() {
        return companion;
    }

    public void setCompanion(Cosmetic companion) {
        this.companion = companion;
    }

    public Cosmetic getParticle() {
        return particle;
    }

    public void setParticle(Cosmetic particle) {
        this.particle = particle;
    }

    public void forEach(BiConsumer<CosmeticSlot, @Nullable Cosmetic> consumer) {
        for (CosmeticSlot slot : CosmeticSlot.values()) {
            consumer.accept(slot, get(slot));
        }
    }

    public static CosmeticState deserialize(Map<String, Object> map) {
        CosmeticState state = new CosmeticState();
        for (CosmeticSlot slot : CosmeticSlot.values()) {
            state.set(slot, Cosmetic.fromQualifiedId((String) map.get(slot.name())).orElse(null));
        }

        return state;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        forEach((slot, cosmetic) -> {
            String id = cosmetic == null ? "EMPTY" : cosmetic.getQualifiedId();
            map.put(slot.name(), id);
        });
        return map;
    }
}
