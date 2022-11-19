package com.minehut.cosmetics.cosmetics.properties;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface SlotHandler {

    Optional<CosmeticSlot> slot();

    void setSlot(@Nullable CosmeticSlot slot);
}