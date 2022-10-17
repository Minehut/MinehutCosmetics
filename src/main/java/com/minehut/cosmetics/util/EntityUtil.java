package com.minehut.cosmetics.util;

import com.minehut.cosmetics.util.data.Key;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;

import java.util.function.Consumer;

public class EntityUtil {

    /**
     * Spawn an entity for use in cosmetics, these entities are
     * not persistent, invincible to damage, and ignore all collisions
     *
     * @param location to spawn the entity at
     * @param clazz    to spawn an entity from
     * @param consumer modify the entity before spawning
     * @param <T>      type of entity to spawn
     * @return the entity that was spawned
     */
    public static <T extends Entity> T spawnCosmeticEntity(Location location, Class<T> clazz, Consumer<T> consumer) {
        return location.getWorld().spawn(location, clazz, (entity) -> {
            consumer.accept(entity);

            // set attributes
            entity.setInvulnerable(true);
            entity.setPersistent(false);
            entity.setSilent(true);

            // living entity uses
            if (entity instanceof LivingEntity living) {
                living.setCollidable(false);
                living.setCanPickupItems(false);
                living.setRemoveWhenFarAway(false);
            }

            // apply data keys
            Key.COSMETIC_ENTITY.write(entity, "");
        });
    }

    /**
     * Check whether this entity is a cosmetic entity or not
     *
     * @param entity to check
     * @return whether the entity is used for cosmetics
     */
    public static boolean isCosmeticEntity(Entity entity) {
        return Key.COSMETIC_ENTITY.read(entity).isPresent();
    }

    /**
     * Spawn an armor stand with the slots locked, used for displaying itemstacks etc
     *
     * @param location to spawn the entity at
     * @param actions  to apply before spawning
     * @return the armor stand
     */
    public static ArmorStand spawnModelStand(Location location, Consumer<ArmorStand> actions) {
        return spawnCosmeticEntity(location, ArmorStand.class, stand -> {
            // lock equipment slots
            for (final EquipmentSlot slot : EquipmentSlot.values()) {
                stand.addEquipmentLock(slot, ArmorStand.LockType.ADDING_OR_CHANGING);
                stand.addEquipmentLock(slot, ArmorStand.LockType.REMOVING_OR_CHANGING);
            }

            stand.setInvisible(true);
            actions.accept(stand);
        });
    }
}
