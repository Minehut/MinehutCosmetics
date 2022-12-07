package com.minehut.cosmetics.cosmetics.entities;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import com.minehut.cosmetics.util.EntityUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class EntityHandler implements Listener {
    private final Set<UUID> entityUUIDs = new HashSet<>();

    @EventHandler
    public void onSpawn(EntitySpawnEvent event) {
        if (!EntityUtil.isCosmeticEntity(event.getEntity())) return;
        entityUUIDs.add(event.getEntity().getUniqueId());
    }

    @EventHandler
    public void onRemove(EntityRemoveFromWorldEvent event) {
        if (!EntityUtil.isCosmeticEntity(event.getEntity())) return;
        entityUUIDs.remove(event.getEntity().getUniqueId());
    }

    public Set<Entity> entities() {
        final Set<Entity> entities = new HashSet<>(entityUUIDs.size());

        // convert the uuids into actual entities
        for (final UUID uuid : entityUUIDs) {
            final Entity entity = Bukkit.getEntity(uuid);
            if (entity == null) {
                continue;
            }
            entities.add(entity);
        }

        return entities;
    }
}
