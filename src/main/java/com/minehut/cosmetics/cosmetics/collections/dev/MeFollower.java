package com.minehut.cosmetics.cosmetics.collections.dev;

import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.companion.Companion;
import com.minehut.cosmetics.cosmetics.types.companion.CompanionCosmetic;
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
                player -> {
                    ItemStack stack = new ItemStack(Material.PLAYER_HEAD);

                    stack.editMeta(meta -> ((SkullMeta) meta).setPlayerProfile(player.getPlayerProfile()));
                    return stack;
                },
                new Vector(0f, -1f, 0f),
                true,
                true,
                true
        );
    }

    @Override
    public Permission permission() {
        return Permission.staff();
    }

    @Override
    public Permission visibility() {
        return Permission.deny();
    }

    @Override
    public ItemStack menuIcon() {
        ItemStack stack = new ItemStack(Material.PLAYER_HEAD);
        stack.editMeta(meta -> meta.displayName(Component.text("Me!")));
        return stack;
    }
}
