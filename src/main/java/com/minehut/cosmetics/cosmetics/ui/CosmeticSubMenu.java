package com.minehut.cosmetics.cosmetics.ui;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.CosmeticsManager;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.properties.ClickHandler;
import com.minehut.cosmetics.cosmetics.properties.CosmeticSlot;
import com.minehut.cosmetics.cosmetics.ui.icons.ItemIcon;
import com.minehut.cosmetics.ui.SubMenu;
import com.minehut.cosmetics.ui.icon.MenuItem;
import com.minehut.cosmetics.util.ItemBuilder;
import com.minehut.cosmetics.util.messaging.Message;
import com.minehut.cosmetics.util.structures.Pair;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class CosmeticSubMenu extends SubMenu {
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

            final Component ownershipText = owns
                    ? Component.text("Click to Equip").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC,
                            false)
                    : Component.text("Not Owned").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false);

            final ItemStack icon = ItemBuilder.of(cosmetic.menuIcon().clone())
                    .appendLore(cosmetic.collection().display())
                    .appendLore(cosmetic.collection().lore())
                    .appendLore(Component.empty())
                    .appendLore(ownershipText)
                    .build();

            items.add(Pair.of(icon, cosmetic));
        }

        // add all the cosmetic items
        items.forEach(entry -> this.items.add(menuItem(entry.left(), entry.right())));
    }

    @Override
    public void render() {
        super.render();
        // Add the UI items
        getProxy().setItem(8, MenuItem.of(ItemIcon.UNEQUIP.get(), (who, click) -> {
            final UUID uuid = who.getUniqueId();

            final CosmeticSlot slot = this.clickHandler.apply(click);
            Cosmetics.get().manager().removeCosmetic(uuid, slot, true);
        }));
    }

    @Override
    public List<MenuItem> getItemList() {
        return items;
    }

    private MenuItem menuItem(ItemStack icon, Cosmetic cosmetic) {
        return MenuItem.of(icon, (player, click) -> Permission.any(Permission.staff(), cosmetic.permission())
                .hasAccess(player).whenComplete((canUse, err) -> {
                    if (err != null) {
                        err.printStackTrace();
                    }

                    if (Boolean.TRUE.equals(canUse)) {
                        final UUID uuid = player.getUniqueId();
                        player.sendMessage(Message.info(Component.text()
                                .append(Component.text("Selected "))
                                .append(cosmetic.name()).append(Component.text("!"))
                                .color(NamedTextColor.AQUA)
                                .build()));

                        final CosmeticSlot slot = clickHandler.apply(click);

                        Bukkit.getScheduler().runTask(Cosmetics.get(), () -> {
                            CosmeticsManager manager = Cosmetics.get().manager();
                            manager.applyCosmetic(uuid, slot, cosmetic);
                            manager.updateEquipment(uuid);
                        });
                        return;
                    }

                    player.closeInventory();
                    player.openBook(BookUI.UNOWNED_CTA);
                }));
    }
}
