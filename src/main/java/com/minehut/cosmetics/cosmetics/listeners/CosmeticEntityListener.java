package com.minehut.cosmetics.cosmetics.listeners;

import com.destroystokyo.paper.event.entity.ProjectileCollideEvent;
import com.minehut.cosmetics.util.EntityUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class CosmeticEntityListener implements Listener {

    /*
     * Damage Events
     */

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!EntityUtil.isCosmeticEntity(event.getEntity())) return;
        event.setDamage(0);
        event.setCancelled(true);
    }

    /*
     * Interaction Events
     */

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        if (!EntityUtil.isCosmeticEntity(event.getRightClicked())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onInteractAt(PlayerInteractAtEntityEvent event) {
        if (!EntityUtil.isCosmeticEntity(event.getRightClicked())) return;
        event.setCancelled(true);
    }

    /*
     * Projectile events
     */

    @EventHandler
    public void onProjectileCollide(ProjectileCollideEvent event) {
        if (!EntityUtil.isCosmeticEntity(event.getCollidedWith())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!EntityUtil.isCosmeticEntity(event.getHitEntity())) return;
        event.setCancelled(true);
    }

    /*
     * Cancel portal events
     */

    @EventHandler
    public void onCosmeticPortalEvent(EntityPortalEvent event) {
        if (!EntityUtil.isCosmeticEntity(event.getEntity())) return;
        event.setCancelled(true);
    }

    /*
     * Cancel burning
     */

    @EventHandler
    public void onEntityBurn(EntityCombustEvent event) {
        if (!EntityUtil.isCosmeticEntity(event.getEntity())) return;
        event.setCancelled(true);
    }
}
