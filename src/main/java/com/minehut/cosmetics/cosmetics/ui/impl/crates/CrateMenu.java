package com.minehut.cosmetics.cosmetics.ui.impl.crates;

import com.minehut.cosmetics.cosmetics.ui.CosmeticMenu;
import com.minehut.cosmetics.crates.Crate;
import com.minehut.cosmetics.crates.CrateType;
import com.minehut.cosmetics.ui.SubMenu;
import com.minehut.cosmetics.ui.icon.MenuItem;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;

public class CrateMenu extends SubMenu {

    private final List<MenuItem> crateItems = new ArrayList<>();

    public CrateMenu() {
        super(Component.text("Crates Menu"), (player, click) -> new CosmeticMenu(player).openTo(player));
        addCrateItem(CrateType.LEGENDARY);
    }

    @Override
    public List<MenuItem> getItemList() {
        return crateItems;
    }

    @Override
    public void render() {
        super.render();
    }

    public void addCrateItem(CrateType type) {
        final Crate crate = type.get();
        final MenuItem menuItem = MenuItem.of(crate.menuIcon(), (who, click) -> {
            crate.open(who.getUniqueId(), 1);
        });
        crateItems.add(menuItem);
    }
}
