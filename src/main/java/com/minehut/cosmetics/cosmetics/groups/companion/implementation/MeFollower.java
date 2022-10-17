package com.minehut.cosmetics.cosmetics.groups.companion.implementation;

import com.minehut.cosmetics.cosmetics.CosmeticPermission;
import com.minehut.cosmetics.cosmetics.groups.companion.Companion;
import com.minehut.cosmetics.cosmetics.groups.companion.CompanionCosmetic;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.Vector;

public class MeFollower extends CompanionCosmetic {
    public MeFollower() {
        super(
                Companion.ME_BUDDY.name(),
                Component.text("Me!"),
                CosmeticPermission.isStaff(),
                player -> {
                    ItemStack stack = new ItemStack(Material.PLAYER_HEAD);

                    stack.editMeta(meta -> ((SkullMeta) meta).setPlayerProfile(player.getPlayerProfile()));
                    return stack;
                },
                new Vector(0f, -.5f, 0f),
                true,
                true,
                true
        );
    }

    @Override
    public ItemStack menuIcon() {
        ItemStack stack = new ItemStack(Material.PLAYER_HEAD);
        stack.editMeta(meta -> meta.displayName(Component.text("Me!")));
        return stack;
    }
}
