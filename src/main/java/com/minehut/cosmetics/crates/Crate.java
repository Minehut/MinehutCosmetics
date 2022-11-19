package com.minehut.cosmetics.crates;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.model.profile.CosmeticData;
import com.minehut.cosmetics.model.profile.CosmeticMeta;
import com.minehut.cosmetics.model.request.ModifyCosmeticQuantityRequest;
import com.minehut.cosmetics.model.request.UnlockCosmeticRequest;
import com.minehut.cosmetics.util.structures.Pair;
import kong.unirest.HttpResponse;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;

import java.util.UUID;

public abstract class Crate extends Cosmetic {

    private final WeightedTable<Pair<CosmeticSupplier<? extends Cosmetic>, Integer>> table;

    protected Crate(String id, Component name, WeightedTable<Pair<CosmeticSupplier<? extends Cosmetic>, Integer>> table) {
        super(id, CosmeticCategory.CRATE, name);
        this.table = table;
    }

    @Override
    public Permission permission() {
        return Permission.none();
    }

    public void open(UUID uuid, int amount) {
        owner(uuid);

        Bukkit.getScheduler().runTaskAsynchronously(Cosmetics.get(), () -> {
            // try to consume the item
            final ModifyCosmeticQuantityRequest req = new ModifyCosmeticQuantityRequest(uuid, category().name(), id(), -amount);
            final HttpResponse<Void> response = Cosmetics.get()
                    .api()
                    .modifyCosmeticQuantity(req)
                    .join();

            // handle consuming response
            switch (response.getStatus()) {
                case 200 -> {
                    // roll for the item reward
                    table.roll().ifPresentOrElse(reward -> {
                        final Cosmetic cosmetic = reward.left().get();
                        final int quantity = reward.right();

                        final CosmeticData data = new CosmeticData(cosmetic.category().name(), cosmetic.id(), new CosmeticMeta(quantity));
                        Cosmetics.get().api().unlockCosmetic(new UnlockCosmeticRequest(uuid, data));

                        final Component message = Component.text()
                                .append(Component.text("Received"))
                                .append(Component.space())
                                .append(cosmetic.name())
                                .append(Component.space())
                                .append(Component.text("x" + quantity))
                                .append(Component.text("!"))
                                .color(NamedTextColor.DARK_GREEN)
                                .build();
                        player().ifPresent(player -> player.sendMessage(message));

                    }, () -> {
                        // let player know if edge case occurs
                        player().ifPresent(player -> player.sendMessage(Component.text("An error occured, please contact an administrator").color(NamedTextColor.RED)));
                    });
                }
                // handle error cases
                case 429 -> {
                    // if they ratelimit
                    player().ifPresent(player -> player.sendMessage(Component.text("Please wait a moment and try again.").color(NamedTextColor.RED)));
                }
                case 412 -> {
                    // let them know they have insufficient resources
                    player().ifPresent(player -> player.sendMessage(Component.text("You do not own enough crates!").color(NamedTextColor.RED)));
                }
            }
        });
    }

    @Override
    public int salvageAmount() {
        return 0;
    }
}