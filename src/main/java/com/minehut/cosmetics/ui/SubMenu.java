package com.minehut.cosmetics.ui;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.config.Mode;
import com.minehut.cosmetics.cosmetics.ui.impl.crates.GemShopMenu;
import com.minehut.cosmetics.cosmetics.ui.impl.crates.SalvageMenu;
import com.minehut.cosmetics.cosmetics.ui.icons.ItemIcon;
import com.minehut.cosmetics.ui.icon.ActionHandler;
import com.minehut.cosmetics.ui.icon.MenuItem;
import com.minehut.cosmetics.ui.icon.MenuItemMultiPageMenu;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.Set;

public abstract class SubMenu extends MenuItemMultiPageMenu<MenuItem> {
    private static final Set<Integer> restricted = Set.of(
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 10, 11, 12, 13, 14, 15, 16, 17,
            43, 44, 45, 46, 47, 48, 49, 40, 51
    );

    private final ActionHandler backAction;

    public SubMenu(Component title, ActionHandler backAction) {
        super(Cosmetics.get(), 6, title);
        this.backAction = backAction;
    }

    @Override
    public void render() {
        super.render();
        getProxy().setItem(0, MenuItem.of(ItemIcon.GO_BACK.get(), backAction));
        getProxy().setItem(1, MenuItem.of(ItemIcon.SHOP_ICON.get(), (player, ignored) -> {
            player.closeInventory();
            player.openBook(Book.book(
                    Component.text("Cosmetics"),
                    Component.text("Minehut"),
                    Component.text("Open Cosmetics Shop ⬈")
                            .style(Style.style(NamedTextColor.BLUE, TextDecoration.UNDERLINED))
                            .clickEvent(ClickEvent.openUrl("https://bit.ly/3TGDqMi"))
            ));
        }));

        if (Cosmetics.mode() == Mode.LOBBY) {
            getProxy().setItem(2, ItemIcon.GEM_SHOP_ICON.get(), (player, ignored) -> GemShopMenu.open(player));
            getProxy().setItem(3, ItemIcon.SALVAGE_ICON.get(), (player, ignored) -> SalvageMenu.open(player));
        }

        for (int idx = 9; idx < 18; idx++) {
            getProxy().setItem(idx, ItemIcon.FILLER_ICON.get());
        }
    }

    @Override
    public int getItemsPerPage() {
        return 27;
    }

    @Override
    public Set<Integer> getRestrictedSlots() {
        return restricted;
    }
}
