package com.minehut.cosmetics.cosmetics.ui.impl.crates;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.ui.CosmeticMenu;
import com.minehut.cosmetics.crates.Crate;
import com.minehut.cosmetics.crates.CrateType;
import com.minehut.cosmetics.model.profile.CosmeticProfile;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CrateMenu extends SubMenu {

    private final List<MenuItem> crateItems = new ArrayList<>();

    private CrateMenu(Player player) {
        super(Component.text("Crates Menu"), (who, click) -> new CosmeticMenu(who).openTo(who));

        final CosmeticProfileResponse response = Cosmetics.get().cosmeticManager().getProfile(player.getUniqueId()).join().orElse(null);
        if (response == null) {
            player.sendMessage(Component.text("Could not retrieve cosmetic profile!").color(NamedTextColor.RED));
            return;
        }


        // get their cosmetics profile
        for (final CrateType type : CrateType.values()) {
            final Crate crate = type.get();
            // if they don't own, can't see, and aren't staff
            if (!Permission.any(crate.permission(), crate.visibility(), Permission.staff()).hasAccess(player).join()) {
                continue;
            }

            final ItemStack icon = ItemBuilder.of(crate.menuIcon())
                    .appendLore(Component.text("Owned: " + response.getQuantity(crate)).color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false))
                    .build();

            final MenuItem menuItem = MenuItem.of(icon, (who, click) -> crate.open(who.getUniqueId(), 1));
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
