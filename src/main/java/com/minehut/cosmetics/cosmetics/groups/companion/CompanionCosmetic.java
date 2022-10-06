package com.minehut.cosmetics.cosmetics.groups.companion;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.groups.follower.MountedFollowerCosmetic;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public abstract class CompanionCosmetic extends MountedFollowerCosmetic {
    /**
     * Class for creating "mounted companions", these are
     * just itemstacks that are equipped on an armor stands head
     * and always face the player
     *
     * @param id                of the cosmetic
     * @param name              of this companion
     * @param permission        required to equip this companion
     * @param companionSupplier build the itemstack for this companion
     * @param offset            to spawn the cosmetic at
     * @param small             whether to use a mini armor stand
     * @param lookX             whether to track rotation on the x axis
     * @param lookY             whether to track rotation on the y axis
     */
    public CompanionCosmetic(String id, Component name, Function<Player, CompletableFuture<Boolean>> permission, Function<Player, ItemStack> companionSupplier, Vector offset, boolean small, boolean lookX, boolean lookY) {
        super(id, CosmeticCategory.COMPANION, name, permission, companionSupplier, offset, small, lookX, lookY);
    }
}
