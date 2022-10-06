package com.minehut.cosmetics.cosmetics;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public abstract class Cosmetic {
    protected final Component name;
    protected final Function<Player, CompletableFuture<Boolean>> permission;
    protected UUID owner;
    private final CosmeticCategory type;
    private final String id;

    protected Cosmetic(final String id,
                       final Component name,
                       final Function<Player, CompletableFuture<Boolean>> permission,
                       final CosmeticCategory type
    ) {
        this.id = id;
        this.name = name;
        this.permission = permission;
        this.type = type;
    }

    public Component name() {
        return name;
    }

    public CompletableFuture<Boolean> canUse(final Player player) {
        return CompletableFuture.supplyAsync(() -> {
            // bypass for staff players
            if (CosmeticPermission.isStaff().apply(player).join()) {
                return true;
            }

            return permission.apply(player).join();
        });
    }

    public Optional<UUID> owner() {
        return Optional.ofNullable(owner);
    }

    public void owner(final UUID owner) {
        this.owner = owner;
    }

    public Optional<Player> player() {
        return owner().map(Bukkit::getPlayer).filter(Player::isOnline);
    }

    public CosmeticCategory category() {
        return type;
    }

    public String id() {
        return id;
    }

    public abstract ItemStack menuIcon();
}
