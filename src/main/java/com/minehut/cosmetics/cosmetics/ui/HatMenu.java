package com.minehut.cosmetics.cosmetics.ui;

import com.minehut.cosmetics.util.ItemBuilder;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.groups.hat.Hat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;


public class HatMenu extends CosmeticSubMenu {

    public static final Supplier<ItemStack> ICON = ItemBuilder.of(Material.DIAMOND_HELMET)
            .flags(ItemFlag.HIDE_ATTRIBUTES)
            .display(Component.text("Hats").color(NamedTextColor.GREEN))
            .lore(
                    Component.empty(),
                    Component.text("Cool hats. Collect them all!").color(NamedTextColor.WHITE),
                    Component.empty()
            ).supplier();

    public HatMenu() {
        super(CosmeticCategory.HAT, 1);
    }

    @Override
    public void render() {
        super.render();

        addCosmetic(
                Hat.EXPLORER
        );
    }

    @Override
    public ItemStack icon() {
        return ICON.get();
    }
}
