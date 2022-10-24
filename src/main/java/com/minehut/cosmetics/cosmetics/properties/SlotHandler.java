package com.minehut.cosmetics.cosmetics.properties;

import com.minehut.cosmetics.cosmetics.equipment.CosmeticSlot;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface SlotHandler {

    Optional<CosmeticSlot> slot();

    void setSlot(@Nullable CosmeticSlot slot);
}