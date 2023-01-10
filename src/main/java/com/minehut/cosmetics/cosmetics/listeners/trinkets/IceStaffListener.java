package com.minehut.cosmetics.cosmetics.listeners.trinkets;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import com.destroystokyo.paper.event.entity.ProjectileCollideEvent;
import com.minehut.cosmetics.cosmetics.collections.winter2022.IceScepterTrinket;
import com.minehut.cosmetics.util.ExpiringSet;
import com.minehut.cosmetics.util.CosmeticUtil;
import com.minehut.cosmetics.util.messaging.Message;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class IceStaffListener implements Listener {

    private final IceScepterTrinket scepter = new IceScepterTrinket();

    private final ExpiringSet<UUID> cooldowns = new ExpiringSet<>(750, TimeUnit.MILLISECONDS);
    private final Set<UUID> snowballs = new HashSet<>();


    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        if (EquipmentSlot.HAND != event.getHand()) {
            return;
        }

        final ItemStack item = event.getItem();
        if (item == null) {
            return;
        }

        CosmeticUtil.readCosmetic(item).ifPresent(cosmetic -> {
            if (!cosmetic.getQualifiedId().equals(scepter.getQualifiedId())) {
                return;
            }

            if (cooldowns.has(player.getUniqueId())) {
                player.sendActionBar(Message.error("Trinket on cooldown."));
                return;
            }

            final Location spawnLocation = player.getEyeLocation()
                    .clone()
                    .add(player.getEyeLocation().getDirection());

            final Vector velocity = player.getEyeLocation().getDirection().multiply(1.5);

            final Entity snowballProjectile = spawnLocation.getWorld().spawn(spawnLocation, Snowball.class, snowball -> {
                snowball.setPersistent(false);
                snowball.setVelocity(velocity);
            });

            cooldowns.add(player.getUniqueId());
            snowballs.add(snowballProjectile.getUniqueId());
        });
    }

    @EventHandler
    public void onSnowballCollide(ProjectileCollideEvent event) {
        if (!snowballs.contains(event.getEntity().getUniqueId())) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onRemoveEntity(EntityRemoveFromWorldEvent event) {
        if (!snowballs.contains(event.getEntity().getUniqueId())) {
            return;
        }

        snowballs.remove(event.getEntity().getUniqueId());
    }
}
