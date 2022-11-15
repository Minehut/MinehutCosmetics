package com.minehut.cosmetics.crates;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Cosmetic;

/**
 * This module should register and handle everything to do with crates
 * in the cosmetics plugin, this module should only be enabled whenever
 * the server is in {@link com.minehut.cosmetics.config.Mode#LOBBY}.
 */
public class CratesModule {

    private final Cosmetics cosmetics;

    public CratesModule(Cosmetics cosmetics) {
        this.cosmetics = cosmetics;
        registerCommands();
    }
    

    private void registerCommands() {

    }
}