package com.minehut.cosmetics.cosmetics.types.balloon;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.types.follower.MountedFollowerCosmetic;
import com.minehut.cosmetics.util.EntityUtil;
import com.minehut.cosmetics.util.data.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class BalloonCosmetic extends MountedFollowerCosmetic {

    private final List<UUID> leashes = new ArrayList<>();
    private boolean equipped = false;

    /**
     * Class for creating "mounted companions", these are
     * just itemstacks that are equipped on an armor stands head
     * and always face the player
     *
     * @param id of the cosmetic
     */
    public BalloonCosmetic(String id) {
        super(id,
                CosmeticCategory.BALLOON,
                new Vector(0, 1.5, 0),
                true,
                true,
                false
        );
    }

    @Override
    public void equip() {
        if (equipped) return;
        equipped = true;
        super.equip();


        player().ifPresent(player -> {
            entities().forEach(entity -> {
                final LivingEntity leash = EntityUtil.spawnCosmeticEntity(entity.getLocation(), Bat.class, (bat) -> {
                    bat.setSilent(true);
                    bat.setInvisible(true);
                    bat.setAI(false);
                    bat.setAware(false);
                    Key.LEASHED.write(bat, "leashed");
                });

                leashes.add(leash.getUniqueId());
                leash.setLeashHolder(player);
                entity.addPassenger(leash);
            });
        });
    }

    @Override
    public void unequip() {
        if (!equipped) return;
        equipped = false;
        super.unequip();

        leashes().forEach(Entity::remove);
    }

    @Override
    public void tick() {
        super.tick();
    }

    public List<LivingEntity> leashes() {
        final List<LivingEntity> leashes = new ArrayList<>();

        for (final UUID uuid : this.leashes) {
            final Entity entity = Bukkit.getEntity(uuid);
            if (!(entity instanceof LivingEntity living)) continue;
            leashes.add(living);
        }

        return leashes;
    }
}
