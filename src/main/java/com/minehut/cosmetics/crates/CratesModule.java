package com.minehut.cosmetics.crates;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.commands.crates.CrateCommand;
import com.minehut.cosmetics.commands.crates.GemShopCommand;
import com.minehut.cosmetics.commands.crates.SalvageCommand;
import com.minehut.cosmetics.crates.listeners.CrateOpeningListener;
import com.minehut.cosmetics.listeners.visibility.CosmeticsVisibilityHandler;
import org.bukkit.Bukkit;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * This module should register and handle everything to do with crates
 * in the cosmetics plugin, this module should only be enabled whenever
 * the server is in {@link com.minehut.cosmetics.config.Mode#LOBBY}.
 */
public class CratesModule {

    private final Cosmetics cosmetics;

    private final Set<UUID> opening = new HashSet<>();


    public CratesModule(Cosmetics cosmetics) {
        this.cosmetics = cosmetics;
        registerCommands();

        Bukkit.getServer().getPluginManager().registerEvents(new CosmeticsVisibilityHandler(cosmetics.cosmeticManager()), cosmetics);
        Bukkit.getServer().getPluginManager().registerEvents(new CrateOpeningListener(this), cosmetics);
    }


    private void registerCommands() {
        new CrateCommand().register(cosmetics);
        new SalvageCommand().register(cosmetics);
        new GemShopCommand().register(cosmetics);
    }

    public Set<UUID> opening() {
        return opening;
    }

    public boolean isOpening(UUID uuid) {
        return opening.contains(uuid);
    }
}