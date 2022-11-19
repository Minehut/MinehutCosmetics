package com.minehut.cosmetics.cosmetics.ui.impl.crates;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.ui.CosmeticMenu;
import com.minehut.cosmetics.currency.Currency;
import com.minehut.cosmetics.model.profile.CosmeticProfileResponse;
import com.minehut.cosmetics.ui.SubMenu;
import com.minehut.cosmetics.ui.icon.MenuItem;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SalvageMenu extends SubMenu {

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
    private SalvageMenu(UUID uuid) {
        super(Component.text("Salvage Menu"), (player, ignored) -> new CosmeticMenu(player).openTo(player));

        final var maybeProfile = Cosmetics.get().cosmeticManager().getProfile(uuid).join();
        if (maybeProfile.isEmpty()) return;

        final CosmeticProfileResponse response = maybeProfile.get();


        setTitle(Component.text()
                .append(Component.text("Salvage "))
                .append(Component.text("- Gems: ").color(NamedTextColor.BLACK))
                .append(Component.text(response.getGems()).color(NamedTextColor.BLACK))
                .append(Currency.GEM.display().color(NamedTextColor.WHITE))
                .build()
        );


        response.getProfile().getPurchased().forEach((category, map) -> map.forEach((id, data) -> {
            // grab the cosmetic from its id
            Cosmetic.fromCategoryId(category, id).ifPresent(cosmetic -> {
                if (cosmetic.salvageAmount() <= 0) return;

                final ItemStack base = cosmetic.menuIcon().clone();
                final Component display = Component.text()
                        .append(cosmetic.name())
                        .append(Component.space())
                        .append(Component.text("x" + data.getMeta().getQuantity()).color(NamedTextColor.GRAY))
                        .build();

                final ItemBuilder builder = ItemBuilder.of(base)
                        .display(display)
                        .appendLore(
                                cosmetic.rarity().display(),
                                Component.text()
                                        .append(Component.text("Salvages for ").color(NamedTextColor.GRAY))
                                        .append(Component.text(cosmetic.salvageAmount()).color(NamedTextColor.AQUA).append(Currency.GEM.display()))
                                        .build(),
                                Component.empty(),
                                SALVAGE_CTA
                        );


                final MenuItem menuItem = MenuItem.of(builder.build(), (who, click) -> {
                    if (!click.isRightClick()) return;
                    new SalvageConfirmMenu(cosmetic).openTo(who);
                });

                items.add(menuItem);
            });
        }));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public List<MenuItem> getItemList() {
        return items;
    }

    public static void open(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(Cosmetics.get(), () -> {
            final SalvageMenu menu = new SalvageMenu(player.getUniqueId());
            Bukkit.getScheduler().runTask(Cosmetics.get(), () -> menu.openTo(player));
        });
    }
}
