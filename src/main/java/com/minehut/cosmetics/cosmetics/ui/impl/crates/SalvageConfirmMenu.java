package com.minehut.cosmetics.cosmetics.ui.impl.crates;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.currency.Currency;
import com.minehut.cosmetics.model.request.SalvageCosmeticRequest;
import com.minehut.cosmetics.ui.icon.ActionHandler;
import kong.unirest.HttpResponse;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

public class SalvageConfirmMenu extends ConfirmationMenu {

    private final Cosmetic cosmetic;

    public SalvageConfirmMenu(Cosmetic cosmetic) {
        super(Component.text("Salvage for ")
                .append(Component.text(cosmetic.salvageAmount()).color(NamedTextColor.AQUA).append(Currency.GEM.display()))
                .append(Component.text("?").color(NamedTextColor.BLACK))
        );
        this.cosmetic = cosmetic;
    }

    @Override
    public void render() {

    }

    @Override
    public ActionHandler onConfirm() {
        return (player, click) -> {
            final SalvageCosmeticRequest req = new SalvageCosmeticRequest(player.getUniqueId(), cosmetic.category().name(), cosmetic.id(), -1, cosmetic.salvageAmount());

            Bukkit.getScheduler().runTaskAsynchronously(Cosmetics.get(), () -> {
                final HttpResponse<Void> res = Cosmetics.get().api().salvageCosmetic(req).join();
                SalvageMenu.open(player);

                switch (res.getStatus()) {
                    case 412 -> player.sendMessage(Component.text("You do not own enough of this cosmetic!").color(NamedTextColor.RED));
                    case 200 -> player.sendMessage(Component.text("Salvaged ")
                            .append(cosmetic.name())
                            .append(Component.text(" for "))
                            .append(Component.text(cosmetic.salvageAmount()))
                            .color(NamedTextColor.DARK_GREEN)
                    );
                }
            });
        };
    }

    @Override
    public ActionHandler onDeny() {
        return null;
    }

    @Override
    public ItemStack displayIcon() {
        return cosmetic.menuIcon();
    }
}
