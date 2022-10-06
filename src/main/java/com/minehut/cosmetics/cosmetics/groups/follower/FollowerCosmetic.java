package com.minehut.cosmetics.cosmetics.groups.follower;

import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.properties.Equippable;
import com.minehut.cosmetics.cosmetics.properties.Tickable;
import com.minehut.cosmetics.util.EntityUtil;
import com.minehut.cosmetics.util.ItemUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public abstract class FollowerCosmetic extends Cosmetic implements Equippable, Tickable {
    protected Function<Player, ItemStack> companionSupplier;


    protected boolean equipped = false;
    protected UUID entityUUID;

    private final Vector offset;

    protected FollowerCosmetic(
            String id,
            CosmeticCategory category,
            final Component name,
            final Function<Player, CompletableFuture<Boolean>> permission,
            Function<Player, ItemStack> companionSupplier,
            Vector offset
    ) {
        super(id, name, permission, category);
        this.companionSupplier = companionSupplier;
        this.offset = offset;
    }

    @Override
    public void equip() {
        if (equipped) return;
        equipped = true;
        // we need the player to spawn the entity
        player().ifPresent(player -> {
            final Location spawnLocation = player.getEyeLocation().clone().subtract(0, 0.5, 0);
            final Vector behind = spawnLocation.getDirection().setY(0).normalize().multiply(-1);
            spawnLocation.add(behind);

            Entity entity = spawnEntity(player, spawnLocation);
            this.entityUUID = entity.getUniqueId();
        });
    }

    @Override
    public void unequip() {
        if (!equipped) return;
        equipped = false;
        entity().ifPresent(Entity::remove);
    }

    public Entity spawnEntity(Player player, Location location) {
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
        player().ifPresentOrElse(player -> entity().ifPresent(entity -> {
            Location target = player.getEyeLocation().clone().subtract(0, 0.5, 0);
            // if the entity is too far away teleport them to the player
            if (entity.getLocation().distanceSquared(target) < 144) {
                moveTowards(entity, target, 0.25, 1);
            } else {
                target.setYaw(0);
                target.setPitch(0);
                entity.teleport(target);
            }
        }), this::unequip);
    }

    public Optional<? extends Entity> entity() {
        return Optional.ofNullable(entityUUID).map(Bukkit::getEntity);
    }

    private void moveTowards(final Entity entity, final Location target, final double speed, final double followDistance) {
        final Location location = entity.getLocation().clone();
        final Vector movement = target.toVector().add(offset).subtract(location.toVector()).normalize().multiply(speed);
        final double distance = entity.getLocation().distance(target);
        final double scalingFactor = Math.max(0, distance <= followDistance ? 0 : Math.log(distance - followDistance));
        final Vector velocity = movement.multiply(scalingFactor);
        entity.setVelocity(velocity);
    }

    public Vector getOffset() {
        return offset;
    }

    public Function<Player, ItemStack> getCompanionSupplier() {
        return companionSupplier;
    }
}
