package com.minehut.cosmetics.cosmetics.collections.beta;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.Rarity;
import com.minehut.cosmetics.ui.model.Model;

import com.minehut.cosmetics.cosmetics.types.companion.CompanionCosmetic;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;

// TODO: Refactor to modern style
public class CompieCompanion extends CompanionCosmetic {
    public CompieCompanion() {
        super(
                com.minehut.cosmetics.cosmetics.types.companion.Companion.EXPLORER.name(),
                Component.text("Compie-chan").color(NamedTextColor.GOLD),
                player -> {
                    ItemStack stack = new ItemStack(Material.SCUTE);
                    stack.editMeta(meta -> {
                        meta.lore(List.of(
                                Component.empty(),
                                Collection.BETA.tag(),
                                Component.empty()
                        ));
                        meta.setCustomModelData(Model.Companion.COMPIE_CHAN);
                    });
                    return stack;
                },
                new Vector(0, -1, 0),
                true,
                true,
                false
        );
    }

    @Override
    public Permission permission() {
        return Permission.hasPurchased(this);
    }

    @Override
    public Permission visibility() {
        return Permission.collectionIsActive(Collection.BETA);
    }

    @Override
    public ItemStack menuIcon() {
        ItemStack stack = new ItemStack(Material.COMPASS);
        stack.editMeta(meta -> {
            meta.displayName(Component.text("Compie-chan").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
            meta.lore(List.of(
                    Component.empty(),
                    Collection.BETA.tag(),
                    Component.empty()
            ));
        });
        return stack;
    }

    @Override
    public Rarity rarity() {
        return Rarity.RARE;
    }
}
