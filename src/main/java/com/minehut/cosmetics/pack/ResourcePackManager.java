package com.minehut.cosmetics.pack;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.events.MinehutPackEvent;
import com.minehut.cosmetics.model.profile.CosmeticProfileResponse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ResourcePackManager implements Listener {

    private final Set<UUID> accepted = new HashSet<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        // check if the player has the pack
        Bukkit.getScheduler().runTaskAsynchronously(Cosmetics.get(), () -> {
            final boolean accepted = fetchPackStatus(event.getPlayer()).join();

            // catch the case where they accepted the pack before the request completes
            if (hasAccepted(event.getPlayer().getUniqueId())) return;
            if (accepted) {
                this.accepted.add(event.getPlayer().getUniqueId());
            }

            // call the pack event depending on their response
            Bukkit.getScheduler().runTask(Cosmetics.get(), () -> {
                final MinehutPackEvent packEvent = new MinehutPackEvent(event.getPlayer(), accepted);
                Bukkit.getServer().getPluginManager().callEvent(packEvent);
            });
        });
    }

    @EventHandler
    public void onPackStatus(PlayerResourcePackStatusEvent event) {
        if (event.getStatus() == PlayerResourcePackStatusEvent.Status.ACCEPTED) return;
        boolean accepted = event.getStatus() == PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED;
        if (accepted) {
            this.accepted.add(event.getPlayer().getUniqueId());
        }

        Bukkit.getServer().getPluginManager().callEvent(new MinehutPackEvent(event.getPlayer(), accepted));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        accepted.remove(event.getPlayer().getUniqueId());
    }

    private CompletableFuture<Boolean> fetchPackStatus(Player player) {
        final boolean localStatus = player.hasResourcePack();
        if (localStatus) {
            return CompletableFuture.completedFuture(true);
        }

        final Optional<CosmeticProfileResponse> maybeResponse = Cosmetics.get()
                .manager()
                .getProfile(player.getUniqueId())
                .join();
        if (maybeResponse.isEmpty()) return CompletableFuture.completedFuture(false);

        final CosmeticProfileResponse response = maybeResponse.get();
        return CompletableFuture.completedFuture(Cosmetics.get().packModule().state().getSha1().equals(response.getPackHash()));
    }

    public boolean hasAccepted(UUID uuid) {
        return accepted.contains(uuid);
    }

    public List<Player> acceptedPlayers() {
        final List<Player> players = new ArrayList<>(accepted.size());

        for (final UUID uuid : this.accepted) {
            final Player player = Bukkit.getPlayer(uuid);
            if (player == null) {
                continue;
            }

            players.add(player);
        }

        return players;
    }
}
