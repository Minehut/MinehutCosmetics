package com.minehut.cosmetics.cosmetics.ui.impl.category;


import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.bindings.MaterialBinding;
import com.minehut.cosmetics.cosmetics.properties.Skinnable;
import com.minehut.cosmetics.cosmetics.ui.CosmeticMenu;
import com.minehut.cosmetics.ui.SubMenu;
import com.minehut.cosmetics.ui.icon.MenuItem;
import com.minehut.cosmetics.util.CosmeticUtil;
import com.minehut.cosmetics.util.ItemBuilder;
import com.minehut.cosmetics.util.messaging.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkinMenu extends SubMenu {
    private static final ItemStack CLEAR_ITEM = ItemBuilder.of(Material.BARRIER).display(Component.text("Remove Skin").color(NamedTextColor.RED)).build();
    private final List<MenuItem> items = new ArrayList<>();

    private final ItemStack item;

    private SkinMenu(Player player, @NotNull ItemStack item) {
        super(Component.text("Skin an item."), (who, click) -> new CosmeticMenu(who).openTo(who));
        this.item = item;

        final Material type = CosmeticUtil.getBaseType(item);

        final Optional<MaterialBinding> maybeBinds = Cosmetics.get().manager().getBindings().getBinding(type);
        if (maybeBinds.isEmpty()) {
            player.sendMessage(Message.error(Component.text("Cannot skin this item.")));
            return;
        }

        final MaterialBinding binds = maybeBinds.get();

        binds.getCosmetics().forEach(supplier -> {
            final Cosmetic cosmetic = supplier.get();
            if (!(Permission.any(cosmetic.permission(), Permission.staff()).hasAccess(player).join())) return;
            if (!(cosmetic instanceof Skinnable skinnable)) return;

            final MenuItem menu = MenuItem.of(cosmetic.menuIcon(), (who, click) -> {

                cosmetic.owner(player.getUniqueId());
                skinnable.applySkin(item);

                player.sendMessage(Message.info(Component.text()
                        .append(Component.text("Applied item skin"))
                        .append(Component.space())
                        .append(cosmetic.name())
                        .build())
                );
                player.getInventory().close();
            });

            this.items.add(menu);
        });
    }

    @Override
    public void render() {
        super.render();

        getProxy().setItem(8, CLEAR_ITEM, (player, click) -> {
            CosmeticUtil.readCosmetic(item).ifPresent(cosmetic -> {
                if (!(cosmetic instanceof Skinnable skinnable)) return;
                cosmetic.owner(player.getUniqueId());
                skinnable.removeSkin(item);
            });

            player.sendMessage(Message.error(Component.text("Removed item skin.").color(NamedTextColor.RED)));
            player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
        });
    }

    @Override
    public List<MenuItem> getItemList() {
        return items;
    }

    public static void open(Player player, ItemStack item) {
        Bukkit.getScheduler().runTaskAsynchronously(Cosmetics.get(), () -> {
            final SkinMenu menu = new SkinMenu(player, item);
            Bukkit.getScheduler().runTask(Cosmetics.get(), () -> menu.openTo(player));
        });
    }
}

