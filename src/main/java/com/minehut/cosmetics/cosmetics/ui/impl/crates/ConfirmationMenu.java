package com.minehut.cosmetics.cosmetics.ui.impl.crates;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.ui.icons.ItemIcon;
import com.minehut.cosmetics.ui.Menu;
import com.minehut.cosmetics.ui.icon.ActionHandler;
import com.minehut.cosmetics.ui.icon.MenuItem;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

public abstract class ConfirmationMenu extends Menu {
    final MenuItem confirmItem = MenuItem.of(ItemIcon.CONFIRM.get(), onConfirm());
    final MenuItem cancelItem = MenuItem.of(ItemIcon.DENY.get(), onDeny());

    public ConfirmationMenu(Component title) {
        super(Cosmetics.get(), 1, title);
    }

    @Override
    public void render() {
        // set up confirm & deny items in the ui
        for (int idx = 0; idx <= 3; idx++) {
            getProxy().setItem(idx, confirmItem);
        }

        for (int idx = 5; idx <= 8; idx++) {
            getProxy().setItem(idx, cancelItem);
        }

        getProxy().setItem(4, displayIcon());
    }

    public abstract ActionHandler onConfirm();

    public abstract ActionHandler onDeny();

    public abstract ItemStack displayIcon();
}
