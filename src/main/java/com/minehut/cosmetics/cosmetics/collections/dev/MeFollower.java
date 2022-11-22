package com.minehut.cosmetics.cosmetics.collections.dev;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.companion.Companion;
import com.minehut.cosmetics.cosmetics.types.companion.CompanionCosmetic;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class MeFollower extends CompanionCosmetic {
    public MeFollower() {
        super(
                Companion.ME_BUDDY.name(),
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
    public Component name() {
        return Component.text("ME").color(rarity().display().color());
    }

    @Override
    public @NotNull ItemStack menuIcon() {

        final ItemBuilder builder = ItemBuilder.of(Material.PLAYER_HEAD)
                .display(name());

        player().ifPresent(builder::playerHead);

        return builder.build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.DEV;
    }
}
