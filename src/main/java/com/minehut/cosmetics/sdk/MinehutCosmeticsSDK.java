package com.minehut.cosmetics.sdk;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.ui.CosmeticMenu;
import org.bukkit.Bukkit;

import java.util.Optional;
import java.util.UUID;

public class MinehutCosmeticsSDK implements CosmeticsSDK {

    private Cosmetics cosmetics;

    public MinehutCosmeticsSDK(Cosmetics cosmetics) {
        this.cosmetics = cosmetics;
    }

    @Override
    public void openCosmeticsMenu(UUID uuid) {
        Optional.ofNullable(Bukkit.getPlayer(uuid)).ifPresent(player -> {
            new CosmeticMenu().openTo(player);
        });
    }
}
