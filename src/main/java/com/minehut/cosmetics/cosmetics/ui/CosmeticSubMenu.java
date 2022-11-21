package com.minehut.cosmetics.cosmetics.ui;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.properties.ClickHandler;
import com.minehut.cosmetics.cosmetics.properties.CosmeticSlot;
import com.minehut.cosmetics.ui.SubMenu;
import com.minehut.cosmetics.ui.icon.MenuItem;
import com.minehut.cosmetics.util.ItemBuilder;
import com.minehut.cosmetics.util.structures.Pair;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class CosmeticSubMenu extends SubMenu {

    private static final ItemStack CLEAR_ITEM =
            ItemBuilder.of(Material.BARRIER)
                    .display(Component.text("Un-Equip").color(NamedTextColor.RED))
                    .build();

    private static final Book CTA_BOOK = Book.book(Component.text("Minehut"), Component.text("Minehut"),
            Component.text()
                    .append(Component.text("You don't own this cosmetic yet!").color(NamedTextColor.BLACK))
                    .append(Component.newline())
                    .append(Component.text("Open Cosmetics Shop ⬈").style(Style.style(NamedTextColor.BLUE, TextDecoration.UNDERLINED)).clickEvent(ClickEvent.openUrl("https://bit.ly/3TGDqMi")))
                    .build()
    );

    private final List<MenuItem> items = new ArrayList<>();

    private final ClickHandler clickHandler;

    public CosmeticSubMenu(CosmeticCategory category,
                           Player player,
                           List<CosmeticSupplier<? extends Cosmetic>> cosmetics,
                           ClickHandler clickHandler) {
        super(Component.text(category.categoryName()), (who, ignored) -> new CosmeticMenu(who).openTo(who));
        this.clickHandler = clickHandler;

        final List<Pair<ItemStack, Cosmetic>> items = new ArrayList<>();

        for (final CosmeticSupplier<? extends Cosmetic> supplier : cosmetics) {
            final Cosmetic cosmetic = supplier.get();

            final boolean owns = Permission.any(Permission.staff(), cosmetic.permission()).hasAccess(player).join();

            // check if the player can see it, has access to it, or if they're staff
            if (!owns && !cosmetic.visibility().hasAccess(player).join()) {
                continue;
            }

            final Component ownText = owns ?
                    Component.text("Owned").color(NamedTextColor.GREEN).decoration(TextDecoration.BOLD, true).decoration(TextDecoration.ITALIC, false)
                    : Component.text("Not Owned").color(NamedTextColor.RED).decoration(TextDecoration.BOLD, true).decoration(TextDecoration.ITALIC, false);

            final ItemStack icon = ItemBuilder.of(cosmetic.menuIcon().clone()).appendLore(ownText).build();

            items.add(Pair.of(icon, cosmetic));
        }

        // add all the cosmetic items
        items.forEach(entry -> this.items.add(menuItem(entry.left(), entry.right())));
    }

    @Override
    public void render() {
        super.render();
        // Add the UI items
        getProxy().setItem(8, MenuItem.of(CLEAR_ITEM, (who, click) -> {
            final UUID uuid = who.getUniqueId();

            final CosmeticSlot slot = this.clickHandler.apply(click);
            Cosmetics.get().cosmeticManager().removeCosmetic(uuid, slot, true);
            who.closeInventory();
        }));
    }

    @Override
    public List<MenuItem> getItemList() {
        return items;
    }

    private MenuItem menuItem(ItemStack icon, Cosmetic cosmetic) {
        return MenuItem.of(icon, (player, click) -> Permission.any(Permission.staff(), cosmetic.permission()).hasAccess(player).whenComplete((canUse, err) -> {
            if (err != null) {
                err.printStackTrace();
            }

            if (canUse) {
                final UUID uuid = player.getUniqueId();
                player.sendMessage(Component.text().append(Component.text("Selected ")).append(cosmetic.name()).append(Component.text("!")).color(NamedTextColor.AQUA).build());

                final CosmeticSlot slot = clickHandler.apply(click);

                Bukkit.getScheduler().runTask(Cosmetics.get(), () -> Cosmetics.get().cosmeticManager().setCosmetic(uuid, slot, cosmetic, true));
                return;
            }

            player.closeInventory();
            player.openBook(CTA_BOOK);
        }));
    }
}
