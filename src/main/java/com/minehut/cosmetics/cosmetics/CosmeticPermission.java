package com.minehut.cosmetics.cosmetics;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.model.profile.CosmeticProfile;
import com.minehut.cosmetics.model.profile.CosmeticProfileResponse;
import com.minehut.cosmetics.model.rank.PlayerRank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
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

    public static Function<Player, CompletableFuture<Boolean>> all(List<Function<Player, CompletableFuture<Boolean>>> requirements) {
        return player -> CompletableFuture.supplyAsync(() -> {
            // convert to a list of futures
            final List<CompletableFuture<Boolean>> futures = new ArrayList<>();
            for (final Function<Player, CompletableFuture<Boolean>> requirement : requirements) {
                futures.add(requirement.apply(player));
            }

            CompletableFuture.allOf(futures.toArray(new CompletableFuture<?>[0])).join();

            // if any of the futures returns false then we want to return false.
            for (final CompletableFuture<Boolean> resultFuture : futures) {
                final boolean result = resultFuture.join();
                if (!result) return false;
            }
            return true;
        });
    }
}
