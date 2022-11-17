package com.minehut.cosmetics.cosmetics.ui;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.model.request.ModifyCosmeticQuantityRequest;
import com.minehut.cosmetics.model.request.SalvageCosmeticRequest;
import com.minehut.cosmetics.ui.Menu;
import com.minehut.cosmetics.ui.icon.MenuItem;
import com.minehut.cosmetics.util.ItemBuilder;
import kong.unirest.HttpResponse;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class SalvageConfirmMenu extends Menu {

    private static final ItemStack CONFIRM = ItemBuilder.of(Material.LIME_STAINED_GLASS_PANE)
            .display(Component.text("Confirm?").color(NamedTextColor.DARK_GREEN))
            .build();

    private static final ItemStack DENY = ItemBuilder.of(Material.RED_STAINED_GLASS_PANE)
            .display(Component.text("Deny?").color(NamedTextColor.RED))
            .build();

    private final UUID uuid;
    private final Cosmetic cosmetic;

    public SalvageConfirmMenu(UUID uuid, Cosmetic cosmetic) {
        super(Cosmetics.get(), 1,
                Component.text("Salvage ")
                        .append(cosmetic.name())
                        .append(Component.text("?").color(NamedTextColor.BLACK))
        );

        this.uuid = uuid;
        this.cosmetic = cosmetic;

        getProxy().setItem(3, MenuItem.of(CONFIRM, (who, click) -> {
            final SalvageCosmeticRequest req = new SalvageCosmeticRequest(who.getUniqueId(), cosmetic.category().name(), cosmetic.id(), 1, cosmetic.salvageAmount());
            final Player player = Bukkit.getPlayer(uuid);

            Bukkit.getScheduler().runTaskAsynchronously(Cosmetics.get(), () -> {
                final HttpResponse<Void> res = Cosmetics.get().api().salvageCosmetic(req).join();
                if (player == null || !player.isOnline()) return;
                CosmeticInventoryMenu.open(player);

                switch (res.getStatus()) {
                    case 412 -> {
                        player.sendMessage(Component.text("You do not own enough of this cosmetic!").color(NamedTextColor.RED));
                    }
                    case 200 -> {
                        player.sendMessage(Component.text("Salvaged ")
                                .append(cosmetic.name())
                                .append(Component.text(" for "))
                                .append(Component.text(cosmetic.salvageAmount()))
                        );
                    }
                }
            });

        }));

        getProxy().setItem(4, cosmetic.menuIcon());

        getProxy().setItem(5, MenuItem.of(CONFIRM, (who, click) -> CosmeticInventoryMenu.open(who)));
    }

    @Override
    public void render() {

    }
}
