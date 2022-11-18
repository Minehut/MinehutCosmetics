package com.minehut.cosmetics.cosmetics.ui.impl.crates;

import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.groups.companion.Companion;
import com.minehut.cosmetics.cosmetics.ui.CosmeticMenu;
import com.minehut.cosmetics.currency.Currency;
import com.minehut.cosmetics.ui.SubMenu;
import com.minehut.cosmetics.ui.icon.MenuItem;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GemShopMenu extends SubMenu {

    private final List<MenuItem> item = new ArrayList<>();

    public GemShopMenu() {
        super(Component.text("Gem Shop"), (player, click) -> new CosmeticMenu(player).openTo(player));

        item.addAll(List.of(
                shopItem(Companion.RED_ROBIN, 500)
        ));
    }

    @Override
    public List<MenuItem> getItemList() {
        return item;
    }

    private MenuItem shopItem(CosmeticSupplier<? extends Cosmetic> supplier, int price) {
        final Cosmetic cosmetic = supplier.get();

        final Component costComponent = Component.text()
                .append(Component.text("Cost: ").color(NamedTextColor.WHITE))
                .append(Component.text(price).append(Currency.GEM.display()))
                .build();

        final ItemStack icon = ItemBuilder.of(cosmetic.menuIcon().clone())
                .appendLore(costComponent)
                .build();

        return MenuItem.of(icon, (who, click) -> new GemShopConfirmPurchase(cosmetic, price));
    }
}
