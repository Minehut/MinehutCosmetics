package com.minehut.cosmetics.cosmetics.ui;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.ui.icon.MenuItem;
import com.minehut.cosmetics.ui.icon.MenuItemMultiPageMenu;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class CosmeticInventoryMenu extends MenuItemMultiPageMenu<MenuItem> {
    private final List<MenuItem> items = new ArrayList<>();

    /**
     * Create a new cosmetic menu for the given user
     * ** SHOULD ONLY BE CALLED ASYNC **
     *
     * @param uuid of the user to build the menu for
     */
    private CosmeticInventoryMenu(UUID uuid) {
        super(Cosmetics.get(), 6, Component.text("Inventory"));

        Cosmetics.get().cosmeticManager().getProfile(uuid).join().ifPresent(response ->
                response.getProfile().getPurchased().forEach((category, map) -> map.forEach((id, data) -> {
                    // grab the cosmetic from its id
                    Cosmetic.fromCategoryId(category, id).ifPresent(cosmetic -> {
                        final ItemStack item = ItemBuilder.of(cosmetic.menuIcon().clone())
                                .appendLore(Component.text("x" + data.getMeta().getQuantity()).color(NamedTextColor.GRAY))
                                .build();
                        items.add(MenuItem.of(item));
                    });
                }))
        );
    }


    @Override
    public List<MenuItem> getItemList() {
        return items;
    }

    @Override
    public int getItemsPerPage() {
        return 36;
    }

    @Override
    public Set<Integer> getRestrictedSlots() {
        return Set.of();
    }

    public static void open(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(Cosmetics.get(), () -> {
            final CosmeticInventoryMenu menu = new CosmeticInventoryMenu(player.getUniqueId());
            Bukkit.getScheduler().runTask(Cosmetics.get(), () -> menu.openTo(player));
        });
    }

}
