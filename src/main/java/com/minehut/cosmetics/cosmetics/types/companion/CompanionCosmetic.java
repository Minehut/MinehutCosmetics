package com.minehut.cosmetics.cosmetics.types.companion;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.types.follower.MountedFollowerCosmetic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.function.Function;

public abstract class CompanionCosmetic extends MountedFollowerCosmetic {
    /**
     * Class for creating "mounted companions", these are
     * just itemstacks that are equipped on an armor stands head
     * and always face the player
     *
     * @param id     of the cosmetic
     * @param offset to spawn the cosmetic at
     * @param small  whether to use a mini armor stand
     * @param lookX  whether to track rotation on the x-axis
     * @param lookY  whether to track rotation on the y-axis
     */
    public CompanionCosmetic(String id, Vector offset, boolean small, boolean lookX, boolean lookY) {
        super(id, CosmeticCategory.COMPANION, offset, small, lookX, lookY);
    }

    @Override
    public List<Function<Player, ItemStack>> getCompanionSuppliers() {
        return List.of(p -> menuIcon());
    }
}
