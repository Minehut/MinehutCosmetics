package com.minehut.cosmetics.events.skins;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticsManager;
import com.minehut.cosmetics.model.profile.CosmeticProfileResponse;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class CosmeticsListener implements Listener {

    private final Plugin plugin;
    private final CosmeticsManager manager;

    public CosmeticsListener(Plugin plugin, CosmeticsManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        // get equipped items for the player
        UUID uuid = event.getPlayer().getUniqueId();

        manager.getProfile(uuid).thenAccept(res -> {
            Bukkit.getScheduler().runTask(plugin, () -> res.map(CosmeticProfileResponse::getEquipped).ifPresent(
                    (equipped) -> equipped.forEach(
                            (category, id) -> CosmeticCategory.getCosmetic(category, id).ifPresent(
                                    cosmetic -> manager.setCosmetic(uuid, cosmetic, false)
                            )
                    )
            ));
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        manager.handleDisconnect(event.getPlayer().getUniqueId());
    }
}
