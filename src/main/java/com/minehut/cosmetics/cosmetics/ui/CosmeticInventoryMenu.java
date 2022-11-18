package com.minehut.cosmetics.cosmetics.ui;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.ui.icons.ItemIcon;
import com.minehut.cosmetics.currency.Currency;
import com.minehut.cosmetics.model.profile.CosmeticProfileResponse;
import com.minehut.cosmetics.ui.icon.MenuItem;
import com.minehut.cosmetics.ui.icon.MenuItemMultiPageMenu;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class CosmeticInventoryMenu extends MenuItemMultiPageMenu<MenuItem> {

    private static final Component SALVAGE_CTA = Component.text()
            .append(Component.keybind("key.use").color(NamedTextColor.YELLOW))
            .append(Component.text(" to salvage.").color(NamedTextColor.GRAY))
            .decoration(TextDecoration.ITALIC, false)
            .build();
    private final List<MenuItem> items = new ArrayList<>();

    /**
     * Create a new cosmetic menu for the given user
     * ** SHOULD ONLY BE CALLED ASYNC **
     *
     * @param uuid of the user to build the menu for
     */
    private CosmeticInventoryMenu(UUID uuid) {
        super(Cosmetics.get(), 6, Component.text("Inventory"));

        final var maybeProfile = Cosmetics.get().cosmeticManager().getProfile(uuid).join();
        if (maybeProfile.isEmpty()) return;

        final CosmeticProfileResponse response = maybeProfile.get();


        response.getProfile().getPurchased().forEach((category, map) -> map.forEach((id, data) -> {
            // grab the cosmetic from its id
            Cosmetic.fromCategoryId(category, id).ifPresent(cosmetic -> {
                final ItemStack base = cosmetic.menuIcon().clone();
                final Component display = Component.text()
                        .append(cosmetic.name())
                        .append(Component.space())
                        .append(Component.text("x" + data.getMeta().getQuantity()).color(NamedTextColor.GRAY))
                        .build();

                final ItemBuilder builder = ItemBuilder.of(base).display(display);

                final boolean salvageable = cosmetic.salvageAmount() > 0;

                // only show salvage cta on items that can be salvaged
                if (salvageable) {
                    builder.appendLore(
                            Component.text()
                                    .append(Component.text("Salvages for ").color(NamedTextColor.GRAY))
                                    .append(Component.text(cosmetic.salvageAmount()).color(NamedTextColor.AQUA).append(Currency.GEM.display()))
                                    .build(),
                            Component.empty(),
                            SALVAGE_CTA
                    );
                }

                final MenuItem menuItem = MenuItem.of(builder.build(), (who, click) -> {
                    if (!click.isRightClick()) return;
                    if (!salvageable) return;
                    new SalvageConfirmMenu(who.getUniqueId(), cosmetic).openTo(who);
                });

                items.add(menuItem);
            });
        }));
    }

    @Override
    public void render() {
        super.render();
        getProxy().setItem(45, MenuItem.of(ItemIcon.GO_BACK.get(), (player, click) -> {
            player.closeInventory();
            new CosmeticMenu(player).openTo(player);
        }));
    }

    @Override
    public List<MenuItem> getItemList() {
        return items;
    }

    @Override
    public int getItemsPerPage() {
        return 45;
    }

    @Override
    public Set<Integer> getRestrictedSlots() {
        return Set.of(45);
    }

    public static void open(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(Cosmetics.get(), () -> {
            final CosmeticInventoryMenu menu = new CosmeticInventoryMenu(player.getUniqueId());
            Bukkit.getScheduler().runTask(Cosmetics.get(), () -> menu.openTo(player));
        });
    }

}
