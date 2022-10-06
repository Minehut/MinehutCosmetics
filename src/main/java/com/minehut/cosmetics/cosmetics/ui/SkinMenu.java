package com.minehut.cosmetics.cosmetics.ui;


import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.CosmeticsManager;
import com.minehut.cosmetics.cosmetics.bindings.MaterialBinding;
import com.minehut.cosmetics.cosmetics.properties.Skinnable;
import com.minehut.cosmetics.menu.Menu;
import com.minehut.cosmetics.menu.ProxyInventory;
import com.minehut.cosmetics.menu.icon.MenuItem;
import com.minehut.cosmetics.modules.KeyManager;
import com.minehut.cosmetics.util.ItemBuilder;
import com.minehut.cosmetics.util.SkinUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class SkinMenu extends Menu {

    private static final Component CTA_TEXT = Component.text()
            .append(Component.text("[Click Here]").decorate(TextDecoration.BOLD).color(NamedTextColor.GOLD))
            .append(Component.space())
            .append(Component.text("to buy cosmetics!").color(NamedTextColor.AQUA))
            .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, "https://bit.ly/3EnE1hq"))
            .build();


    private static final ItemStack CTA_HEAD = ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE)
            .display(Component.text("Click to buy skins @ cosmetics.minehut.com")
                    .decoration(TextDecoration.ITALIC, false).color(NamedTextColor.AQUA)).build();

    private static final MenuItem CTA_MENU_ITEM = MenuItem.of(CTA_HEAD, (player, ignored) -> {
        player.closeInventory();
        player.sendMessage(CTA_TEXT);
    });

    private static final ItemStack CLEAR_ITEM = ItemBuilder.of(Material.BARRIER).display(Component.text("Remove Skin").color(NamedTextColor.RED)).build();
    private final HashSet<CosmeticSupplier> cosmetics = new HashSet<>();

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

        this.cosmetics.addAll(new HashSet<>(maybeBinds.get().getCosmetics()));
        cosmetics.removeIf(cosmetic -> !cosmetic.get().canUse(player).join());

        int rows = Math.max(1, (int) Math.ceil(this.cosmetics.size() / 9f));
        setProxy(new ProxyInventory(rows));
    }

    @Override
    public void render() {
        int slot = 0;
        for (CosmeticSupplier supplier : cosmetics) {
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
            // grab the cosmetic id and category from the items data
            KeyManager keys = Cosmetics.get().keyManager();

            // grab the cosmetic from the persistent data
            PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
            String category = data.get(keys.COSMETIC_CATEGORY, PersistentDataType.STRING);
            String id = data.get(keys.COSMETIC_ID, PersistentDataType.STRING);
            if (category == null || id == null) return;

            // remove the cosmetic skin
            CosmeticCategory.getCosmetic(category, id).ifPresent(cosmetic -> {
                if (!(cosmetic instanceof Skinnable skinnable)) return;
                cosmetic.owner(player.getUniqueId());
                skinnable.removeSkin(item);
                player.sendMessage(Component.text("Removed item skin.").color(NamedTextColor.RED));
            });

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

    public static CompletableFuture<Void> open(Plugin plugin, CosmeticsManager manager, Player player, ItemStack item) {
        return create(plugin, manager, player, item).thenAccept((menu) -> Bukkit.getScheduler().runTask(plugin, () -> menu.openTo(player)));
    }
}

