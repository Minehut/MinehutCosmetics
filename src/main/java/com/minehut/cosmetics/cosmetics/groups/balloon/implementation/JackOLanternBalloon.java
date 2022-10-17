package com.minehut.cosmetics.cosmetics.groups.balloon.implementation;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticPermission;
import com.minehut.cosmetics.cosmetics.Model;
import com.minehut.cosmetics.cosmetics.groups.balloon.Balloon;
import com.minehut.cosmetics.cosmetics.groups.balloon.BalloonCosmetic;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public class JackOLanternBalloon extends BalloonCosmetic {

    private static final Component NAME = Component.text("Jack-o-Lantern Balloon")
            .decoration(TextDecoration.ITALIC, false)
            .color(NamedTextColor.GOLD);

    private static final Supplier<ItemStack> ITEM = ItemBuilder.of(Material.SCUTE)
            .display(NAME)
            .lore(
                    Component.empty(),
                    Component.text("Spooktacular 2022").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
                    Component.empty()
            )
            .modelData(Model.Balloon.JACK_O_LANTERN)
            .supplier();

    public JackOLanternBalloon() {
        super(
                Balloon.JACK_O_LANTERN.name(),
                NAME,
                CosmeticPermission.hasPurchased(CosmeticCategory.BALLOON.name(), Balloon.JACK_O_LANTERN.name()),
                p -> ITEM.get()
        );
    }

    @Override
    public ItemStack menuIcon() {
        return ITEM.get();
    }
}