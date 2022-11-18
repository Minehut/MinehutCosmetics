package com.minehut.cosmetics.cosmetics.ui.impl.crates;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.currency.Currency;
import com.minehut.cosmetics.model.profile.CosmeticData;
import com.minehut.cosmetics.model.profile.CosmeticMeta;
import com.minehut.cosmetics.model.request.ModifyCosmeticQuantityRequest;
import com.minehut.cosmetics.model.request.UnlockCosmeticRequest;
import com.minehut.cosmetics.ui.icon.ActionHandler;
import kong.unirest.HttpResponse;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

public class GemShopConfirmPurchase extends ConfirmationMenu {

    private final Cosmetic cosmetic;
    private final int price;

    public GemShopConfirmPurchase(Cosmetic cosmetic, int price) {
        super(Component.text()
                .append(Component.text("Confirm purchase for "))
                .append(Component.text()
                        .append(Component.text(price).color(NamedTextColor.BLACK))
                        .append(Currency.GEM.display().color(NamedTextColor.WHITE))
                        .append(Component.text("?").color(NamedTextColor.BLACK))
                        .build()
                ).build()
        );
        this.cosmetic = cosmetic;
        this.price = price;
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public ActionHandler onConfirm() {
        return (player, type) -> Bukkit.getScheduler().runTaskAsynchronously(Cosmetics.get(), () -> {
            // try to take the amount of gems that we need
            final ModifyCosmeticQuantityRequest req = new ModifyCosmeticQuantityRequest(player.getUniqueId(),
                    "CURRENCY",
                    Currency.GEM.id(),
                    -price
            );

            final HttpResponse<Void> response = Cosmetics.get()
                    .api()
                    .modifyCosmeticQuantity(req)
                    .join();

            // handle consuming response
            switch (response.getStatus()) {
                case 200 -> {
                    final CosmeticData data = new CosmeticData(cosmetic.category().name(), cosmetic.id(), new CosmeticMeta(1));
                    Cosmetics.get().api().unlockCosmetic(new UnlockCosmeticRequest(player.getUniqueId(), data));
                }
                // handle error cases
                case 429 -> // if they ratelimit
                        player.sendMessage(Component.text("Please wait a moment and try again.").color(NamedTextColor.RED));
                case 412 -> // let them know they have insufficient resources
                        player.sendMessage(Component.text("You don't have enough gems!").color(NamedTextColor.RED));
            }

            Bukkit.getScheduler().runTask(Cosmetics.get(), () -> new GemShopMenu().openTo(player));
        });
    }

    @Override
    public ActionHandler onDeny() {
        return (player, type) -> new GemShopMenu().openTo(player);
    }

    @Override
    public ItemStack displayIcon() {
        return cosmetic.menuIcon();
    }
}
