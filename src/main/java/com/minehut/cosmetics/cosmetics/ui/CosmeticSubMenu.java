package com.minehut.cosmetics.cosmetics.ui;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.menu.Menu;
import com.minehut.cosmetics.menu.icon.MenuItem;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public abstract class CosmeticSubMenu extends Menu {

    private final CosmeticCategory category;

    public CosmeticSubMenu(CosmeticCategory category, int rows) {
        super(Cosmetics.get(), rows, category.getCategoryName());
        this.category = category;
        setupRenderTask(0, 10);
    }

    @Override
    public void render() {
        getProxy().setItem(getProxy().getSize() - 1, MenuItem.of(ItemBuilder.of(Material.BARRIER).display(Component.text("Clear Item").color(NamedTextColor.RED)).build(), (player, click) -> {
            final UUID uuid = player.getUniqueId();

            Cosmetics.get().cosmeticManager().removeCosmetic(uuid, category, true);
            player.sendMessage(Component.text("Item removed.").color(NamedTextColor.AQUA));
            player.closeInventory();
        }));
    }

    protected final void addCosmetic(CosmeticSupplier... suppliers) {
        for (int idx = 0; idx < suppliers.length; idx++) {
            getProxy().setItem(idx, menuItem(suppliers[idx]));
        }
    }

    protected MenuItem menuItem(CosmeticSupplier supplier) {
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
