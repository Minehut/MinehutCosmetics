package com.minehut.cosmetics.cosmetics.ui;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.crates.Crate;
import com.minehut.cosmetics.crates.CrateType;
import com.minehut.cosmetics.ui.Menu;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.Plugin;

public class CrateMenu extends Menu {
    public CrateMenu() {
        super(Cosmetics.get(), 1, Component.text("Crates Menu"));
    }

    @Override
    public void render() {
        addCrateItem(CrateType.TEST);
    }

    public void addCrateItem(CrateType type) {
        final Crate crate = type.get();
        getProxy().addItem(crate.menuIcon(), (who, click) -> {
            crate.open(who.getUniqueId(), 1);
        });
    }
}
