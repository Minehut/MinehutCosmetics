package com.minehut.cosmetics.cosmetics;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.model.profile.CosmeticData;
import com.minehut.cosmetics.model.profile.CosmeticProfile;
import com.minehut.cosmetics.model.profile.CosmeticProfileResponse;
import com.minehut.cosmetics.model.rank.PlayerRank;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface Permission {

    /**
     * Checks whether the given player has access to this permission
     *
     * @param player to check for
     * @return a future containing whether the player has access or not.
     */
    CompletableFuture<Boolean> hasAccess(Player player);


    /**
     * An empty permission that always contains the result "true"
     *
     * @return a permission that is always true.
     */
    static Permission none() {
        return player -> CompletableFuture.completedFuture(true);
    }

    /**
     * Create a permission that is always false
     *
     * @return permission that's always false
     */
    static Permission deny() {
        return player -> CompletableFuture.completedFuture(false);
    }

    static Permission uuid(UUID uuid) {
        return player -> CompletableFuture.completedFuture(uuid.equals(player.getUniqueId()));
    }


    static Permission collectionIsActive(Collection collection) {
        return player -> CompletableFuture.completedFuture(Collection.isActive(collection));
    }

    /**
     * A permission that checks if a given player has a specified rank
     *
     * @param name of the rank to check the player for
     * @return a permission that checks for this rank
     */
    static Permission hasRank(String name) {
        return player -> Cosmetics.get()
                .cosmeticManager()
                .getProfile(player.getUniqueId())
                .thenApplyAsync(maybeProfile -> maybeProfile
                        .flatMap((res) -> PlayerRank.getBackingRank(res.getRank())) // get rank for this player
                        .map((res) -> res.hasAccess(name)) // check if rank has access
                        .orElse(false) // default to false if a call fails
                );
    }


    /**
     * Check if the given player is staff
     *
     * @return a permission that validates whether player are staff
     */
    static Permission staff() {
        return player -> hasRank("MOD").hasAccess(player);
    }

    static Permission hasResourcePack() {
        return player -> {
            final boolean localStatus = player.hasResourcePack();
            if (localStatus) {
                return CompletableFuture.completedFuture(true);
            }

            final Optional<CosmeticProfileResponse> maybeResponse = Cosmetics.get()
                    .cosmeticManager()
                    .getProfile(player.getUniqueId())
                    .join();
            if (maybeResponse.isEmpty()) return CompletableFuture.completedFuture(false);

            final CosmeticProfileResponse response = maybeResponse.get();
            return CompletableFuture.completedFuture(Cosmetics.get().packModule().state().getSha1().equals(response.getPackHash()));
        };
    }


    /**
     * Check if a given player has purchased the given item.
     *
     * @param category of the item to check purchases for
     * @param id       of the purchased item.
     * @return a permission for whether a player has purhcased the given item.
     */
    static Permission hasPurchased(final String category, final String id) {
        return player -> Cosmetics.get()
                .cosmeticManager()
                .getProfile(player.getUniqueId())
                .thenApplyAsync((maybeResponse) ->
                        maybeResponse.map(CosmeticProfileResponse::getProfile)
                                .map(CosmeticProfile::getPurchased)
                                .map(purchased -> purchased.get(category))
                                .map(cosmeticCategory -> cosmeticCategory.get(id))
                                .map(CosmeticData::getMeta)
                                .map(meta -> meta.getQuantity() > 0)
                                .orElse(false)
                );
    }

    /**
     * Check if a given player has purchased the given item.
     *
     * @param category of the item to check purchases for
     * @param id       of the purchased item.
     * @return a permission for whether a player has purhcased the given item.
     */
    static Permission hasPurchased(final CosmeticCategory category, final String id) {
        return hasPurchased(category.name(), id);
    }

    static Permission hasPurchased(final Cosmetic cosmetic) {
        return hasPurchased(cosmetic.category(), cosmetic.id());
    }

    /**
     * Compound permisison for checking if *all* passed {@link Permission} are true.
     *
     * @param permissions to check
     * @return whether all passed {@link Permission} return true.
     */
    static Permission all(List<Permission> permissions) {
        return player -> CompletableFuture.supplyAsync(() -> {
            // convert to a list of futures
            final List<CompletableFuture<Boolean>> futures = new ArrayList<>();
            for (final Permission requirement : permissions) {
                futures.add(requirement.hasAccess(player));
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

    static Permission all(Permission... permissions) {
        return all(List.of(permissions));
    }


    /**
     * Compound permission where if any {@link Permission} result returns true, we return true.
     *
     * @param permissions to check
     * @return whether any {@link  Permission} passsed in was true
     */
    static Permission any(List<Permission> permissions) {
        return player -> CompletableFuture.supplyAsync(() -> {

            final List<CompletableFuture<Boolean>> futures = getResultFutures(player, permissions);
            CompletableFuture.allOf(futures.toArray(new CompletableFuture<?>[0])).join();

            // if any of the futures return true, we want to return true
            for (final CompletableFuture<Boolean> resultFuture : futures) {
                if (resultFuture.join()) return true;
            }
            return false;
        });
    }

    static Permission any(Permission... permissions) {
        return any(List.of(permissions));
    }

    /**
     * Convert a list of permissions to a list of future permission results for a given player.
     *
     * @param player      to create permission futures for
     * @param permissions to generate futures for
     * @return a list of the futures relevent to this player
     */
    private static List<CompletableFuture<Boolean>> getResultFutures(Player player, List<Permission> permissions) {
        // convert to a list of futures
        final List<CompletableFuture<Boolean>> futures = new ArrayList<>();
        for (final Permission requirement : permissions) {
            futures.add(requirement.hasAccess(player));
        }
        return futures;
    }
}
