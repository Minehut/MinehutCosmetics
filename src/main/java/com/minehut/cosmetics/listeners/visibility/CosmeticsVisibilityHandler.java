package com.minehut.cosmetics.listeners.visibility;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.util.EntityUtil;
import com.minehut.cosmetics.util.Version;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("UnstableApiUsage")
public class CosmeticsVisibilityHandler implements Listener {

    private final Cosmetics cosmetics;

    public CosmeticsVisibilityHandler(Cosmetics cosmetics) {
        this.cosmetics = cosmetics;
    }

    @EventHandler
    public void unequipOnSpectate(PlayerGameModeChangeEvent event) {
        cosmetics.manager().unEquipAll(event.getPlayer().getUniqueId());
        if (event.getNewGameMode() == GameMode.SPECTATOR) return;
        cosmetics.manager().equipAll(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void hideOnEntitySpawn(EntitySpawnEvent event) {
        if (!Version.V_1_19.isSupported()) return;
        if (!EntityUtil.isCosmeticEntity(event.getEntity())) return;

        for (final Player player : Bukkit.getOnlinePlayers()) {
            Bukkit.getScheduler().runTaskAsynchronously(Cosmetics.get(), () -> {
                final boolean accepted = hasResourcePack(player).join();
                if (accepted) return;
                player.hideEntity(cosmetics, event.getEntity());
            });
        }
    }

    @EventHandler
    public void showOnAcceptPack(PlayerResourcePackStatusEvent event) {
        if (!Version.V_1_19.isSupported()) return;
        if (event.getStatus() != PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED) return;

        for (final Entity entity : cosmetics.entityHandler().entities()) {
            event.getPlayer().showEntity(cosmetics, entity);
        }
    }

    @EventHandler
    public void hideOnJoin(PlayerJoinEvent event) {
        if (!Version.V_1_19.isSupported()) return;
        Bukkit.getScheduler().runTaskAsynchronously(Cosmetics.get(), () -> {
            // check if they have the pack
            final boolean accepted = hasResourcePack(event.getPlayer()).join();
            if (accepted) return;

            // if they do not have hte pack, hide all entities
            Bukkit.getScheduler().runTask(Cosmetics.get(), () -> {
                for (final Entity entity : cosmetics.entityHandler().entities()) {
                    event.getPlayer().hideEntity(cosmetics, entity);
                }
            });
        });
    }

    private CompletableFuture<Boolean> hasResourcePack(Player player) {
        return Permission.hasResourcePack().hasAccess(player);
    }
}
