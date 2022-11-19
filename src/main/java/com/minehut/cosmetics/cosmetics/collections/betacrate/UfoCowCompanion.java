package com.minehut.cosmetics.cosmetics.collections.betacrate;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.Rarity;
import com.minehut.cosmetics.cosmetics.types.companion.Companion;
import com.minehut.cosmetics.cosmetics.types.companion.CompanionCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class UfoCowCompanion extends CompanionCosmetic {


    private static final Component NAME = Component.text("Mooon Cow")
            .color(NamedTextColor.GOLD)
            .decoration(TextDecoration.ITALIC, false);

    private static final Supplier<ItemStack> ICON = ItemBuilder.of(Material.SCUTE)
            .display(NAME)
            .modelData(Model.Companion.UFO_COW)
            .lore(
                    Component.empty(),
                    Collection.MINEHUT_LEGENDARY_CRATE.tag(),
                    Component.empty()
            ).supplier();


    public UfoCowCompanion() {
        super(Companion.UFO_COW.name(),
                NAME,
                (p) -> ICON.get(),
                new Vector(0, -1, 0),
                true,
                true,
                false
        );
    }

    @Override
    public Permission visibility() {
        return Permission.collectionIsActive(Collection.MINEHUT_LEGENDARY_CRATE);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ICON.get();
    }

    @Override
    public Rarity rarity() {
        return Rarity.RARE;
    }
}
