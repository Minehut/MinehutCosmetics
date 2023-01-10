package com.minehut.cosmetics.cosmetics.collections.winter2023;

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

public class IceSpellbookTrinket extends ItemTrinket {
    public IceSpellbookTrinket() {
        super(Trinket.ICE_SPELLBOOK.name());
    }

    @Override
    public Component name() {
        return Component.text("Ice Spellbook")
                .decoration(TextDecoration.ITALIC, false)
                .color(rarity().display().color());
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.SCUTE)
                .display(name())
                .modelData(Model.Trinket.ICE_SPELLBOOK)
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.WINTER_2023;
    }
}
