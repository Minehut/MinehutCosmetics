package com.minehut.cosmetics.cosmetics.groups.companion.implementation;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticPermission;
import com.minehut.cosmetics.cosmetics.Model;
import com.minehut.cosmetics.cosmetics.groups.companion.Companion;
import com.minehut.cosmetics.cosmetics.groups.companion.CompanionCosmetic;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;

public class CompieCompanion extends CompanionCosmetic {
    public CompieCompanion() {
        super(
                Companion.EXPLORER.name(),
                Component.text("Compie-chan").color(NamedTextColor.GOLD),
                CosmeticPermission.hasPurchased(CosmeticCategory.COMPANION.name(), Companion.EXPLORER.name()),
                player -> {
                    ItemStack stack = new ItemStack(Material.SCUTE);
                    stack.editMeta(meta -> {
                        meta.lore(List.of(
                                Component.empty(),
                                Component.text("Minehut Cosmetic: Beta").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
                                Component.empty()
                        ));
                        meta.setCustomModelData(Model.COMPANION.COMPIE_CHAN);
                    });
                    return stack;
                },
                new Vector(0, -1.5, 0),
                true,
                true,
                false
        );
    }

    @Override
    public ItemStack menuIcon() {
        ItemStack stack = new ItemStack(Material.COMPASS);
        stack.editMeta(meta -> {
            meta.displayName(Component.text("Compie-chan").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
            meta.lore(List.of(
                    Component.empty(),
                    Component.text("Minehut Cosmetic: Beta").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
                    Component.empty()
            ));
        });
        return stack;
    }
}
