package com.minehut.cosmetics.cosmetics.properties;

import com.minehut.cosmetics.cosmetics.groups.equipment.CosmeticSlot;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface SlotHandler {

    Optional<CosmeticSlot> slot();

    void setSlot(@Nullable CosmeticSlot slot);
}