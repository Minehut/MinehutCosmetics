package com.minehut.cosmetics.cosmetics.ui;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.config.Mode;
import com.minehut.cosmetics.menu.Menu;
import com.minehut.cosmetics.menu.icon.MenuItem;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
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

    public CosmeticMenu() {
        super(Cosmetics.get(), 1, "Cosmetics");
    }

    @Override
    public void render() {
        final Mode mode = Cosmetics.get().config().mode();

        addMenu(ParticleMenu::new);
        addMenu(CompanionMenu::new);
        addMenu(BalloonMenu::new);
        addMenu(TrinketMenu::new);

        // menus that are only visible when in lobby mode
        if (Mode.LOBBY == mode) {
            addMenu(ItemMenu::new);
            addMenu(HatMenu::new);
        }

        if (Mode.PLAYER_SERVER == mode) {
            getProxy().addItem(MenuItem.of(SKIN_INFO));
        }
    }

    private void addMenu(Supplier<CosmeticSubMenu> supplier) {
        final CosmeticSubMenu menu = supplier.get();
        getProxy().addItem(menu.icon(), (player, ignored) -> menu.openTo(player));
    }
}
