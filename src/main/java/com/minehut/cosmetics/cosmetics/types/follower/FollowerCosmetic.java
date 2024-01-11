package com.minehut.cosmetics.cosmetics.types.follower;

import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.properties.Equippable;
import com.minehut.cosmetics.cosmetics.properties.Tickable;
import com.minehut.cosmetics.util.EntityUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public abstract class FollowerCosmetic extends Cosmetic implements Equippable, Tickable {
    protected List<UUID> entityUUIDs;

    protected boolean equipped = false;

    private final Vector offset;

    protected FollowerCosmetic(
            String id,
            CosmeticCategory category,
            Vector offset) {
        super(id, category);
        this.offset = offset;
        entityUUIDs = new ArrayList<>();
    }

    @Override
    public void equip() {
        if (equipped) {
            return;
        }

        equipped = true;
        // we need the player to spawn the entity
        player().ifPresent(player -> {
            for (Function<Player, ItemStack> companionSupplier : getCompanionSuppliers()) {
                final Location spawnLocation = player.getEyeLocation().clone().subtract(0, 0.5, 0);
                final Vector behind = spawnLocation.getDirection().setY(0).normalize().multiply(-1);
                spawnLocation.add(behind);
                final Vector random = new Vector(Math.random() - 0.5, 0, Math.random() - 0.5);
                spawnLocation.add(random);
                spawnLocation.add(offset);

                Entity entity = spawnEntity(player, spawnLocation, companionSupplier);
                entityUUIDs.add(entity.getUniqueId());
            }
        });
    }

    @Override
    public void unequip() {
        if (!equipped) {
            return;
        }

        equipped = false;
        entities().forEach(Entity::remove);
    }

    public Entity spawnEntity(Player player, Location location, Function<Player, ItemStack> companionSupplier) {
        return EntityUtil.spawnCosmeticEntity(location, Item.class, item -> {
            item.setItemStack(companionSupplier.apply(player));
            item.setCanPlayerPickup(false);
            item.setCanMobPickup(false);
            item.setWillAge(false);
            item.setGravity(false);
        });
    }

    public void tick() {
        // process if the player is present
        player().ifPresentOrElse(player -> entities().forEach(entity -> {
            Location target = player.getEyeLocation().clone();
            // if the entity is too far away teleport them to the player
            if (entity.getLocation().distanceSquared(target) < 144) {
                entity.setVelocity(moveTowards(entity, target, 0.25, 1));
            } else {
                target.setYaw(0);
                target.setPitch(0);

                // unequip to de-spawn then re-equip to spawn near the player
                unequip();
                equip();
            }

            // Move away from each other
            for (Entity otherEntity : entities()) {
                if (entity.equals(otherEntity)) {
                    continue;
                }

                // Rarely they can get stacked on top of each other, this adds some random
                // velocity to move them away from each other
                if (entity.getLocation().getX() == otherEntity.getLocation().getX()
                        && entity.getLocation().getZ() == otherEntity.getLocation().getZ()) {
                    final Vector random = new Vector(Math.random() - 0.5, 0, Math.random() - 0.5);
                    entity.setVelocity(entity.getVelocity().add(random));
                }

                double speed = getMoveAwaySpeed(entity.getLocation().distanceSquared(otherEntity.getLocation()));

                // Negative speed to move away
                entity.setVelocity(entity.getVelocity().add(moveAway(entity, otherEntity.getLocation(), speed)));
            }
        }), this::unequip);
    }

    public List<? extends Entity> entities() {
        final List<Entity> entities = new ArrayList<>(entityUUIDs.size());
        for (final UUID uuid : entityUUIDs) {
            final Entity entity = Bukkit.getEntity(uuid);
            if (entity == null)
                continue;
            entities.add(entity);
        }
        return entities;
    }

    @SuppressWarnings("SameParameterValue")
    private Vector moveTowards(final Entity entity, final Location target, final double speed,
            final double followDistance) {
        final Location location = entity.getLocation().clone();
        final Vector movement = target.toVector().add(offset).subtract(location.toVector()).normalize().multiply(speed);
        final double distance = entity.getLocation().distance(target);
        final double scalingFactor = Math.max(0, distance <= followDistance ? 0 : Math.log(distance - followDistance));
        return movement.multiply(scalingFactor);
    }

    private Vector moveAway(final Entity entity, final Location target, final double speed) {
        final Location location = entity.getLocation().clone();
        final Vector movement = target.toVector().add(offset).subtract(location.toVector()).normalize().multiply(speed);
        return movement.multiply(-1).setY(0);
    }

    /**
     * Takes distance of two entities squared and outputs a speed with this formula:
     * y = -0.0625x + 0.25
     * This locks the output speed (y) between 0.25 (the max move towards player
     * speed) and 0,
     * between the distance (x) ranges of 0 and 4.
     * <p>
     * <a href="https://www.desmos.com/calculator/hrkyyewmzc">Calculator</a>
     *
     * @param distanceSquared the distance between the two entities, squared
     * @return the speed to move away at
     */
    private double getMoveAwaySpeed(double distanceSquared) {
        return Math.max((distanceSquared * -0.0625d) + 0.25d, 0);
    }

    public Vector getOffset() {
        return offset;
    }

    public List<Function<Player, ItemStack>> getCompanionSuppliers() {
        return List.of(p -> menuIcon());
    }
}
