package com.minehut.cosmetics.cosmetics.ui.impl.crates;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.currency.Currency;
import com.minehut.cosmetics.model.profile.CosmeticData;
import com.minehut.cosmetics.model.profile.CosmeticMeta;
import com.minehut.cosmetics.model.request.ModifyCosmeticQuantityRequest;
import com.minehut.cosmetics.model.request.UnlockCosmeticRequest;
import com.minehut.cosmetics.ui.icon.ActionHandler;
import com.minehut.cosmetics.util.messaging.Message;
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
                    .networkApi()
                    .modifyCosmeticQuantity(req)
                    .join();

            // handle consuming response
            switch (response.getStatus()) {
                case 200 -> {
                    final CosmeticData data = new CosmeticData(cosmetic.category().name(), cosmetic.id(), new CosmeticMeta(1));
                    Cosmetics.get().networkApi().unlockCosmetic(new UnlockCosmeticRequest(player.getUniqueId(), data));

                    final Component content = Component.text()
                            .append(Component.text("Gem Shop").color(NamedTextColor.GREEN))
                            .append(Component.newline())
                            .append(Component.newline())
                            .append(Component.text("Purchased ").color(NamedTextColor.WHITE))
                            .append(cosmetic.name())
                            .append(Component.text(" for "))
                            .append(Component.text(price))
                            .append(Currency.GEM.display())
                            .append(Component.newline())
                            .append(Component.newline())
                            .append(Component.text("Type "))
                            .append(Component.text("/cosmetics").color(NamedTextColor.YELLOW))
                            .build();
                    player.sendMessage(Message.announcement(content));
                }
                // handle error cases
                case 429 -> // if they ratelimit
                        player.sendMessage(Message.error("Please wait a moment and try again."));
                case 412 -> // let them know they have insufficient resources
                        player.sendMessage(Message.error("You don't have enough gems!"));
            }

            Bukkit.getScheduler().runTask(Cosmetics.get(), () -> GemShopMenu.open(player));
        });
    }

    @Override
    public ActionHandler onDeny() {
        return (player, type) -> GemShopMenu.open(player);
    }

    @Override
    public ItemStack displayIcon() {
        return cosmetic.menuIcon();
    }
}
