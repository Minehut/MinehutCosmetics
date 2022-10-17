package com.minehut.cosmetics.cosmetics.groups.trinket;

import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public abstract class TrinketCosmetic extends Cosmetic {
    protected TrinketCosmetic(String id, Component name, Function<Player, CompletableFuture<Boolean>> permission) {
        super(id, name, permission, CosmeticCategory.TRINKET);
    }
}
