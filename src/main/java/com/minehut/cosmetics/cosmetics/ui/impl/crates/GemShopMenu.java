package com.minehut.cosmetics.cosmetics.ui.impl.crates;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.types.companion.Companion;
import com.minehut.cosmetics.cosmetics.types.hat.Hat;
import com.minehut.cosmetics.cosmetics.types.item.Item;
import com.minehut.cosmetics.cosmetics.types.wing.Wing;
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

public class GemShopMenu extends SubMenu {

    private final List<MenuItem> item = new ArrayList<>();

    private GemShopMenu(UUID uuid) {
        super(Component.text("Gem Shop"), (player, click) -> new CosmeticMenu(player).openTo(player));

        final var maybeProfile = Cosmetics.get().manager().getProfile(uuid).join();
        if (maybeProfile.isEmpty()) return;
        final CosmeticProfileResponse response = maybeProfile.get();

        setTitle(Component.text()
                .append(Component.text("Gem Shop"))
                .append(Component.text(" - Gems: ").color(NamedTextColor.BLACK))
                .append(Component.text(response.getGems()).color(NamedTextColor.BLACK))
                .append(Currency.GEM.display().color(NamedTextColor.WHITE))
                .build()
        );

        item.addAll(List.of(
                shopItem(Item.DRAGON_SWORD, 500),
                shopItem(Companion.YOUNG_DRAGON, 500),
                shopItem(Hat.DRAGON, 500),
                shopItem(Wing.DRAGON, 500)
        ));
    }

    @Override
    public List<MenuItem> getItemList() {
        return item;
    }

    private MenuItem shopItem(CosmeticSupplier<? extends Cosmetic> supplier, int price) {
        final Cosmetic cosmetic = supplier.get();

        final Component costComponent =
                Component.text()
                        .append(Component.text("Price: ").color(NamedTextColor.GRAY))
                        .append(Component.text(price).color(NamedTextColor.AQUA))
                        .append(Currency.GEM.display())
                        .decoration(TextDecoration.ITALIC, false)
                        .build();

        final ItemStack icon = ItemBuilder.of(cosmetic.menuIcon().clone())
                .appendLore(
                        cosmetic.collection().display(),
                        Component.empty(),
                        costComponent
                )
                .build();

        return MenuItem.of(icon, (who, click) -> new GemShopConfirmPurchase(cosmetic, price).openTo(who));
    }

    public static void open(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(Cosmetics.get(), () -> {
            final GemShopMenu menu = new GemShopMenu(player.getUniqueId());
            Bukkit.getScheduler().runTask(Cosmetics.get(), () -> menu.openTo(player));
        });
    }
}
