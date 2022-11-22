package com.minehut.cosmetics.cosmetics.collections.dev;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.types.item.Item;
import com.minehut.cosmetics.cosmetics.types.wing.WingCosmetic;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class KatanaBack extends WingCosmetic {

    private final Katana katana = new Katana();

    public KatanaBack() {
        super(Item.KATANA.name());
    }

    @Override
    public Permission permission() {
        return Permission.uuid(UUID.fromString("0cab222b-63ce-46c0-ada3-a56365f2dc8a"));
    }

    @Override
    public Permission visibility() {
        return Permission.deny();
    }

    @Override
    public Component name() {
        return katana.name();
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return katana.menuIcon();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.DEV;
    }
}