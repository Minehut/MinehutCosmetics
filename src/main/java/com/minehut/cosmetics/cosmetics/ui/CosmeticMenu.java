package com.minehut.cosmetics.cosmetics.ui;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.config.Mode;
import com.minehut.cosmetics.menu.Menu;
import com.minehut.cosmetics.menu.icon.MenuItem;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public class CosmeticMenu extends Menu {

    private static final ItemStack SKIN_INFO = ItemBuilder.of(Material.PLAYER_HEAD)
            .display(Component.text("How to apply skins.").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false))
            .lore(
                    Component.empty(),
                    Component.text("Hold the item you'd like to skin and type ")
                            .color(NamedTextColor.AQUA)
                            .append(Component.text("/skin")
                                    .color(NamedTextColor.GOLD)
                                    .decorate(TextDecoration.BOLD))
            )
            .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjcwNWZkOTRhMGM0MzE5MjdmYjRlNjM5YjBmY2ZiNDk3MTdlNDEyMjg1YTAyYjQzOWUwMTEyZGEyMmIyZTJlYyJ9fX0=")
            .build();

    public CosmeticMenu(Player player) {
        super(Cosmetics.get(), 1, "Cosmetics");


        final Mode mode = Cosmetics.get().config().mode();

        addMenu(ParticleMenu.ICON, () -> new ParticleMenu(player));
        addMenu(CompanionMenu.ICON, () -> new CompanionMenu(player));
        addMenu(BalloonMenu.ICON, () -> new BalloonMenu(player));
        addMenu(TrinketMenu.ICON, () -> new TrinketMenu(player));
        addMenu(BackpieceMenu.ICON, () -> new BackpieceMenu(player));

        // menus that are only visible when in lobby mode
        if (Mode.LOBBY == mode) {
            addMenu(ItemMenu.ICON, () -> new ItemMenu(player));
            addMenu(HatMenu.ICON, () -> new HatMenu(player));
        }

        if (Mode.PLAYER_SERVER == mode) {
            getProxy().addItem(MenuItem.of(SKIN_INFO));
        }
    }

    @Override
    public void render() {
    }

    private void addMenu(ItemStack icon, Supplier<CosmeticSubMenu> supplier) {
        getProxy().addItem(icon, (player, ignored) -> {
            // create menus async then open them
            Bukkit.getScheduler().runTaskAsynchronously(Cosmetics.get(), () -> {
                final CosmeticSubMenu subMenu = supplier.get();
                Bukkit.getScheduler().runTask(Cosmetics.get(), () -> subMenu.openTo(player));
            });
        });
    }
}
