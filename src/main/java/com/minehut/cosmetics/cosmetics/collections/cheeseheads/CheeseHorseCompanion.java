package com.minehut.cosmetics.cosmetics.collections.cheeseheads;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.types.companion.Companion;
import com.minehut.cosmetics.cosmetics.types.companion.CompanionCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class CheeseHorseCompanion extends CompanionCosmetic {

    public CheeseHorseCompanion() {
        super(Companion.CHEESE_HORSE.name(),
            new Vector(0, 0, 0),
            true,
            true,
            false);
    }

    @Override
    public Component name() {
        return Component.text("Cheese Horse");
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        ItemStack item = new ItemStack(Material.SCUTE);
        item.editMeta(meta -> meta.setCustomModelData(Model.Companion.CHEESE_HORSE));
        return item;
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.CHEESE_HEADS;
    }
}
