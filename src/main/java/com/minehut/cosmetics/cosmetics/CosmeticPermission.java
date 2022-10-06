package com.minehut.cosmetics.cosmetics;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.model.profile.CosmeticProfile;
import com.minehut.cosmetics.model.profile.CosmeticProfileResponse;
import com.minehut.cosmetics.model.rank.PlayerRank;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class CosmeticPermission {
    private CosmeticPermission() {
    }

    public static Function<Player, CompletableFuture<Boolean>> none() {
        return player -> CompletableFuture.completedFuture(true);
    }

    public static Function<Player, CompletableFuture<Boolean>> hasRank(String name) {
        return player -> Cosmetics.get()
                .cosmeticManager()
                .getProfile(player.getUniqueId())
                .thenApplyAsync(maybeProfile -> maybeProfile
                        .flatMap((res) -> PlayerRank.getBackingRank(res.getRank())) // get rank for this player
                        .map((res) -> res.hasAccess(name)) // check if rank has access
                        .orElse(false) // default to false if a call fails
                );
    }

    public static Function<Player, CompletableFuture<Boolean>> isStaff() {
        return player -> hasRank("MOD").apply(player);
    }

    public static Function<Player, CompletableFuture<Boolean>> hasPurchased(final String category, final String id) {
        return player -> Cosmetics.get()
                .cosmeticManager()
                .getProfile(player.getUniqueId())
                .thenApplyAsync((maybeResponse) ->
                        maybeResponse.map(CosmeticProfileResponse::getProfile)
                                .map(CosmeticProfile::getPurchased)
                                .map((purchased) -> purchased.get(category))
                                .map((cosmeticCategory) -> cosmeticCategory.containsKey(id))
                                .orElse(false)
                );
    }
}
