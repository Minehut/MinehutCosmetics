package com.minehut.cosmetics.cosmetics.ui.impl.crates;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.currency.Currency;
import com.minehut.cosmetics.model.request.SalvageCosmeticRequest;
import com.minehut.cosmetics.ui.icon.ActionHandler;
import com.minehut.cosmetics.util.messaging.Message;
import kong.unirest.HttpResponse;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

public class SalvageConfirmMenu extends ConfirmationMenu {

    private final Cosmetic cosmetic;

    public SalvageConfirmMenu(Cosmetic cosmetic) {
        super(Component.text("Salvage for ")
                .append(Component.text()
                        .append(Component.text(cosmetic.salvageAmount()))
                        .append(Currency.GEM.display().color(NamedTextColor.WHITE))
                        .append(Component.text("?").color(NamedTextColor.BLACK))
                        .build()
                )
        );
        this.cosmetic = cosmetic;
    }

    @Override
    public ActionHandler onConfirm() {
        return (player, click) -> {
            final SalvageCosmeticRequest req = new SalvageCosmeticRequest(player.getUniqueId(), cosmetic.category().name(), cosmetic.id(), -1, cosmetic.salvageAmount());

            Bukkit.getScheduler().runTaskAsynchronously(Cosmetics.get(), () -> {
                final HttpResponse<Void> res = Cosmetics.get().api().salvageCosmetic(req).join();
                switch (res.getStatus()) {
                    case 412 ->
                            player.sendMessage(Component.text("You do not own enough of this cosmetic!").color(NamedTextColor.RED));
                    case 200 -> {
                        final Component content = Component.text()
                                .append(Component.text("Salvaged Cosmetic").color(NamedTextColor.GREEN))
                                .append(Component.newline())
                                .append(Component.newline())
                                .append(Component.text("You salvaged").color(NamedTextColor.WHITE))
                                .append(Component.space())
                                .append(cosmetic.name())
                                .append(Component.space())
                                .append(Component.text("for").color(NamedTextColor.WHITE))
                                .append(Component.space())
                                .append(Component.text(cosmetic.salvageAmount()).color(NamedTextColor.LIGHT_PURPLE))
                                .append(Currency.GEM.display())
                                .append(Component.newline())
                                .append(Component.newline())
                                .append(Component.text("Type"))
                                .append(Component.space())
                                .append(Component.text("/cosmetics").color(NamedTextColor.YELLOW))
                                .build();
                        player.sendMessage(Message.announcement(content));
                    }
                }

                Bukkit.getScheduler().runTask(Cosmetics.get(), () -> SalvageMenu.open(player));
            });
        };
    }

    @Override
    public ActionHandler onDeny() {
        return (who, click) -> SalvageMenu.open(who);
    }

    @Override
    public ItemStack displayIcon() {
        return cosmetic.menuIcon();
    }
}
