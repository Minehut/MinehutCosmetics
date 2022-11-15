package com.minehut.cosmetics.cosmetics.ui;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.crates.Crate;
import com.minehut.cosmetics.ui.Menu;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.Plugin;

public class CrateMenu extends Menu {
    public CrateMenu() {
        super(Cosmetics.get(), 1, Component.text("Crates Menu"));
    }

    @Override
    public void render() {

    }

    public void addCrateItem(Crate crate) {

    }
}
