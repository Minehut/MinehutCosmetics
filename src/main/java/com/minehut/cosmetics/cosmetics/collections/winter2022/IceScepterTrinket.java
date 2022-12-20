package com.minehut.cosmetics.cosmetics.collections.winter2022;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.types.trinket.ItemTrinket;
import com.minehut.cosmetics.cosmetics.types.trinket.Trinket;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class IceScepterTrinket extends ItemTrinket {
    public IceScepterTrinket() {
        super(Trinket.ICE_SCEPTER.name());
    }

    @Override
    public Component name() {
        return Component.text("Ice Scepter")
                .decoration(TextDecoration.ITALIC, false)
                .color(rarity().display().color());
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.SCUTE)
                .display(name())
                .modelData(Model.Trinket.ICE_SCEPTER)
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.ICE;
    }
}
