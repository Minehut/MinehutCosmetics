package com.minehut.cosmetics.cosmetics.ui;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticPermission;
import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.groups.equipment.ClickHandler;
import com.minehut.cosmetics.cosmetics.groups.equipment.CosmeticSlot;
import com.minehut.cosmetics.ui.Menu;
import com.minehut.cosmetics.ui.icon.MenuItem;
import com.minehut.cosmetics.util.ItemBuilder;
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

public abstract class CosmeticSubMenu extends Menu {

    private static final ItemStack CLEAR_ITEM =
            ItemBuilder.of(Material.BARRIER)
                    .display(Component.text("Clear Item").color(NamedTextColor.RED))
                    .build();
    private static final ItemStack BACK_ITEM =
            ItemBuilder.of(Material.DARK_OAK_DOOR)
                    .display(Component.text("Go Back").color(NamedTextColor.RED))
                    .build();

    private final ClickHandler clickHandler;

    public CosmeticSubMenu(CosmeticCategory category,
                           Player player,
                           List<CosmeticSupplier<? extends Cosmetic>> cosmetics,
                           ClickHandler clickHandler) {
        super(Cosmetics.get(), 1, category.categoryName());
        this.clickHandler = clickHandler;

        final List<MenuItem> items = new ArrayList<>();

        for (final CosmeticSupplier<? extends Cosmetic> supplier : cosmetics) {
            if (!supplier.isVisible() && !CosmeticPermission.isStaff().apply(player).join()) continue;
            items.add(menuItem(supplier));
        }

        int size = (int) Math.max(1, Math.ceil((items.size() + 2) / 9f));
        setRows(size);

        getProxy().setItem(size * 9 - 2, MenuItem.of(CLEAR_ITEM, (who, click) -> {
            final UUID uuid = who.getUniqueId();

            final CosmeticSlot slot = this.clickHandler.apply(click);
            Cosmetics.get().cosmeticManager().removeCosmetic(uuid, slot, true);

            who.sendMessage(Component.text("Item removed.").color(NamedTextColor.AQUA));
            who.closeInventory();
        }));

        getProxy().setItem(size * 9 - 1, MenuItem.of(BACK_ITEM, (who, click) -> new CosmeticMenu(who).openTo(who)));

        items.forEach(getProxy()::addItem);

        setupRenderTask(0, 10);
    }

    @Override
    public void render() {

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

                final CosmeticSlot slot = clickHandler.apply(click);

                Bukkit.getScheduler().runTask(Cosmetics.get(), () -> Cosmetics.get().cosmeticManager().setCosmetic(uuid, slot, cosmetic, true));
            } else {
                player.sendMessage(
                        Component.text()
                                .append(Component.text("You don't own this cosmetic yet!").color(NamedTextColor.YELLOW))
                                .append(Component.newline())
                                .append(Component.text("Open Cosmetics Shop ⬈").style(Style.style(NamedTextColor.BLUE, TextDecoration.UNDERLINED)).clickEvent(ClickEvent.openUrl("https://bit.ly/3TGDqMi"))
                                )
                );
            }
            Bukkit.getScheduler().runTask(Cosmetics.get(), () -> player.closeInventory());
        }));
    }
}
