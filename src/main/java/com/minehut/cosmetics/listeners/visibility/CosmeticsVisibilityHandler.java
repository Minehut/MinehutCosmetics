package com.minehut.cosmetics.listeners.visibility;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.CosmeticsManager;
import com.minehut.cosmetics.util.EntityUtil;
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

@SuppressWarnings("UnstableApiUsage")
public class CosmeticsVisibilityHandler implements Listener {

    private final CosmeticsManager manager;

    public CosmeticsVisibilityHandler(CosmeticsManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onSpectator(PlayerGameModeChangeEvent event) {
        manager.unEquipAll(event.getPlayer().getUniqueId());
        if (event.getNewGameMode() == GameMode.SPECTATOR) return;
        manager.equipAll(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        Bukkit.getScheduler().runTask(Cosmetics.get(), () -> {
            if (!EntityUtil.isCosmeticEntity(event.getEntity())) return;
            for (Player player : Bukkit.getOnlinePlayers()) if (!hasResourcePack(player)) player.hideEntity(Cosmetics.get(), event.getEntity());
        });
    }

    @EventHandler
    public void onResourcePackStatusChange(PlayerResourcePackStatusEvent event) {
        if (event.getStatus() == PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED)
            for (Entity entity : event.getPlayer().getWorld().getEntities()) if (EntityUtil.isCosmeticEntity(entity))
                event.getPlayer().showEntity(Cosmetics.get(), entity);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (hasResourcePack(event.getPlayer())) return;
        for (Entity entity : event.getPlayer().getWorld().getEntities()) {
            if (!EntityUtil.isCosmeticEntity(entity)) continue;
            event.getPlayer().hideEntity(Cosmetics.get(), entity);
        }
    }

    private boolean hasResourcePack(Player player) {
        return player.hasResourcePack();
    }
}
