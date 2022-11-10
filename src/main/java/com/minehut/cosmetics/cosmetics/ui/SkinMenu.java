package com.minehut.cosmetics.cosmetics.ui;


import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.CosmeticsManager;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.bindings.MaterialBinding;
import com.minehut.cosmetics.cosmetics.properties.Skinnable;
import com.minehut.cosmetics.ui.Menu;
import com.minehut.cosmetics.ui.ProxyInventory;
import com.minehut.cosmetics.ui.icon.MenuItem;
import com.minehut.cosmetics.util.ItemBuilder;
import com.minehut.cosmetics.util.SkinUtil;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class SkinMenu extends Menu {
    private static final Book CTA_BOOK = Book.book(
            Component.text("Minehut"),
            Component.text("Minehut"),
            Component.text()
                    .append(Component.text("Open Cosmetics Shop ⬈").style(Style.style(NamedTextColor.BLUE, TextDecoration.UNDERLINED)).clickEvent(ClickEvent.openUrl("https://bit.ly/3TGDqMi")))
                    .build()
    );

    private static final ItemStack CTA_HEAD = ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE)
            .display(Component.text("Click to buy skins!")
                    .decoration(TextDecoration.ITALIC, false)
                    .color(NamedTextColor.AQUA))
            .build();

    private static final MenuItem CTA_MENU_ITEM = MenuItem.of(CTA_HEAD, (player, ignored) -> player.openBook(CTA_BOOK));

    private static final ItemStack CLEAR_ITEM = ItemBuilder.of(Material.BARRIER).display(Component.text("Remove Skin").color(NamedTextColor.RED)).build();
    private final HashSet<CosmeticSupplier<? extends Cosmetic>> cosmetics = new HashSet<>();

    @NotNull
    private final ItemStack item;
    private boolean noBindings = false;

    private SkinMenu(Plugin plugin, CosmeticsManager manager, Player player, @NotNull ItemStack item) {
        super(plugin, 1, "Skinning Menu");
        this.item = item;

        final Material type = SkinUtil.getBaseType(item);

        final Optional<MaterialBinding> maybeBinds = manager.getBindings().getBinding(type);
        if (maybeBinds.isEmpty()) {
            noBindings = true;
            return;
        }

        for (final CosmeticSupplier<? extends Cosmetic> cosmetic : Set.copyOf(maybeBinds.get().getCosmetics())) {
            if (!(cosmetic.get().permission().hasAccess(player).join() || Permission.staff().hasAccess(player).join())) {
                continue;
            }
            cosmetics.add(cosmetic);
        }

        int rows = Math.max(1, (int) Math.ceil(this.cosmetics.size() / 9f));
        setProxy(new ProxyInventory(rows));
    }

    @Override
    public void render() {
        int slot = 0;
        for (CosmeticSupplier<? extends Cosmetic> supplier : cosmetics) {
            Cosmetic cosmetic = supplier.get();
            if (!(cosmetic instanceof Skinnable skinnable)) return;
            getProxy().setItem(slot, MenuItem.of(cosmetic.menuIcon(), (player, ignored) -> {

                cosmetic.owner(player.getUniqueId());
                skinnable.applySkin(item);

                player.sendMessage(Component.text().append(Component.text("Applied item skin")).append(Component.space()).append(cosmetic.name()).color(NamedTextColor.GREEN).build());
                player.getInventory().close();
            }));

            slot++;
        }

        // fill the rest with the menu w/ the cta
        for (int idx = slot; idx < getProxy().getSize() - 1; idx++) {
            getProxy().setItem(idx, CTA_MENU_ITEM);
        }

        getProxy().setItem(getProxy().getSize() - 1, CLEAR_ITEM, (player, click) -> {
            SkinUtil.getCosmetic(item).ifPresent(cosmetic -> {
                if (!(cosmetic instanceof Skinnable skinnable)) return;
                cosmetic.owner(player.getUniqueId());
                skinnable.removeSkin(item);
            });

            player.sendMessage(Component.text("Removed item skin.").color(NamedTextColor.RED));
            player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
        });
    }

    @Override
    public void openTo(Player player) {
        if (noBindings) {
            player.sendMessage(Component.text("This item cannot be skinned!").color(NamedTextColor.RED));
            return;
        }

        super.openTo(player);
    }

    public static CompletableFuture<SkinMenu> create(Plugin plugin, CosmeticsManager manager, Player player, ItemStack item) {
        return CompletableFuture.supplyAsync(() -> new SkinMenu(plugin, manager, player, item));
    }

    public static void open(Plugin plugin, CosmeticsManager manager, Player player, ItemStack item) {
        create(plugin, manager, player, item).thenAccept((menu) -> Bukkit.getScheduler().runTask(plugin, () -> menu.openTo(player)));
    }
}

