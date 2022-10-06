package com.minehut.cosmetics.cosmetics.groups.balloon;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.groups.follower.MountedFollowerCosmetic;
import com.minehut.cosmetics.util.EntityUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public abstract class BalloonCosmetic extends MountedFollowerCosmetic {

    private Entity handle;

    private boolean equipped = false;

    /**
     * Class for creating "mounted companions", these are
     * just itemstacks that are equipped on an armor stands head
     * and always face the player
     *
     * @param id              of the cosmetic
     * @param name            of this companion
     * @param permission      required to equip this companion
     * @param balloonSupplier build the itemstack for this companion
     */
    public BalloonCosmetic(String id, Component name, Function<Player, CompletableFuture<Boolean>> permission, Function<Player, ItemStack> balloonSupplier) {
        super(id, CosmeticCategory.BALLOON, name, permission, balloonSupplier, new Vector(0, 2, 0), true, true, false);
    }

    @Override
    public void equip() {
        if (equipped) return;
        equipped = true;
        super.equip();

        player().ifPresent(player -> {
            final Location spawn = player.getLocation().add(getOffset());

            this.handle = EntityUtil.spawnCosmeticEntity(spawn, Parrot.class, entity -> {
                entity.setInvisible(true);
                entity.setLeashHolder(player);
                entity.getPersistentDataContainer().set(Cosmetics.get().keyManager().LEASHED, PersistentDataType.STRING, "true");
            });
        });
    }

    @Override
    public void unequip() {
        if (!equipped) return;
        equipped = false;
        super.unequip();
        handle().ifPresent(Entity::remove);
    }

    @Override
    public void tick() {
        super.tick();
        entity().ifPresent(entity -> handle().ifPresent(handle -> handle.teleport(entity.getLocation().add(0, 0.5, 0))));
    }

    public Optional<Entity> handle() {
        return Optional.ofNullable(handle);
    }

}
