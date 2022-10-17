package com.minehut.cosmetics.cosmetics.ui;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.menu.Menu;
import com.minehut.cosmetics.menu.ProxyInventory;
import com.minehut.cosmetics.menu.icon.MenuItem;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class CosmeticSubMenu extends Menu {

    private static final ItemStack CLEAR_ITEM = ItemBuilder.of(Material.BARRIER).display(Component.text("Clear Item").color(NamedTextColor.RED)).build();

    private final CosmeticCategory category;

    public CosmeticSubMenu(CosmeticCategory category, List<CosmeticSupplier<? extends Cosmetic>> cosmetics) {
        super(Cosmetics.get(), 2, category.categoryName());
        this.category = category;


        final List<CosmeticSupplier<? extends Cosmetic>> available = new ArrayList<>();

        for (final CosmeticSupplier<? extends Cosmetic> supplier : cosmetics) {
            if (!supplier.isVisible()) continue;
            available.add(supplier);
        }
        available.forEach(cosmetic -> getProxy().addItem(menuItem(cosmetic)));

        setupRenderTask(0, 10);
    }

    @Override
    public void render() {
        getProxy().setItem(getProxy().getSize() - 1, MenuItem.of(CLEAR_ITEM, (player, click) -> {
            final UUID uuid = player.getUniqueId();

            Cosmetics.get().cosmeticManager().removeCosmetic(uuid, category, true);
            player.sendMessage(Component.text("Item removed.").color(NamedTextColor.AQUA));
            player.closeInventory();
        }));
    }

    protected MenuItem menuItem(CosmeticSupplier<? extends Cosmetic> supplier) {
        Cosmetic cosmetic = supplier.get();
        return MenuItem.of(cosmetic.menuIcon(), (player, click) -> cosmetic.canUse(player).whenComplete((canUse, err) -> {
            if (err != null) {
                err.printStackTrace();
            }

            if (canUse) {
                final UUID uuid = player.getUniqueId();
                player.sendMessage(Component.text().append(Component.text("Selected ")).append(cosmetic.name()).append(Component.text("!")).color(NamedTextColor.AQUA).build());

                Bukkit.getScheduler().runTask(Cosmetics.get(), () -> Cosmetics.get().cosmeticManager().setCosmetic(uuid, cosmetic, true));
            } else {
                player.sendMessage(Component.text("You don't own this cosmetic yet!").color(NamedTextColor.RED));
            }

            Bukkit.getScheduler().runTask(Cosmetics.get(), () -> player.closeInventory());
        }));
    }

    public abstract ItemStack icon();
}
