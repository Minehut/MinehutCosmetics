package com.minehut.cosmetics.cosmetics.ui.impl.crates;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.ui.BookUI;
import com.minehut.cosmetics.cosmetics.ui.CosmeticMenu;
import com.minehut.cosmetics.crates.Crate;
import com.minehut.cosmetics.crates.CrateType;
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

public class CrateMenu extends SubMenu {

    private final List<MenuItem> crateItems = new ArrayList<>();

    private CrateMenu(Player player) {
        super(Component.text("Crates Menu"), (who, click) -> new CosmeticMenu(who).openTo(who));

        final CosmeticProfileResponse response = Cosmetics.get().manager().getProfile(player.getUniqueId()).join().orElse(null);
        if (response == null) {
            player.sendMessage(Component.text("Could not retrieve cosmetic profile!").color(NamedTextColor.RED));
            return;
        }


        // get their cosmetics profile
        for (final CrateType type : CrateType.values()) {
            final Crate crate = type.get();

            final boolean visible = crate.visibility().hasAccess(player).join();
            if (!visible) continue;

            int qtyOwned = response.getQuantity(crate);
            final boolean openable = qtyOwned > 0;


            final Component openCTA = openable ?
                    Component.text("Click to open").color(NamedTextColor.GREEN)
                    : Component.text("Click to buy").color(NamedTextColor.GREEN);

            final Component display = openable ? crate.name() : crate.name().color(NamedTextColor.GRAY);

            final ItemStack builder = ItemBuilder.of(crate.menuIcon().clone())
                    .display(display)
                    .appendLore(
                            Component.empty(),
                            openCTA.decoration(TextDecoration.ITALIC, false),
                            Component.text("" + qtyOwned + " Available").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)
                    )
                    .build();

            final MenuItem menuItem = MenuItem.of(builder, (who, click) -> {
                if (!openable) {
                    player.closeInventory();
                    player.openBook(BookUI.COSMETICS_STORE_URL);
                    return;
                }

                crate.open(player.getUniqueId(), 1);
            });

            crateItems.add(menuItem);
        }
    }

    @Override
    public List<MenuItem> getItemList() {
        return crateItems;
    }

    public static void open(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(Cosmetics.get(), () -> {
            final CrateMenu menu = new CrateMenu(player);
            Bukkit.getScheduler().runTask(Cosmetics.get(), () -> menu.openTo(player));
        });
    }
}
