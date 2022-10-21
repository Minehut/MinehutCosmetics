package com.minehut.cosmetics.cosmetics.groups.trinket;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.config.Mode;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.properties.Equippable;
import com.minehut.cosmetics.util.SkinUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public abstract class TrinketCosmetic extends Cosmetic implements Equippable {
    protected TrinketCosmetic(String id, Component name, Function<Player, CompletableFuture<Boolean>> permission) {
        super(id, name, permission, CosmeticCategory.TRINKET);
    }

    @Override
    public void equip() {
        if (Cosmetics.get().config().mode() == Mode.LOBBY) {
            player().ifPresent(player -> {
                player.getInventory().setItem(6, menuIcon());
                player.getInventory().setHeldItemSlot(6);
            });
        } else {
            player().ifPresent(player -> {
                final ItemStack item = menuIcon();
                item.editMeta(meta -> SkinUtil.writeCosmeticKeys(meta, this));
                player.getInventory().addItem(item);
            });
        }
    }

    @Override
    public void unequip() {
        // we don't unequip trinkets, we just give them to the player
    }
}
