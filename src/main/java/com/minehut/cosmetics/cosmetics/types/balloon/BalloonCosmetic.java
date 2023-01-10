package com.minehut.cosmetics.cosmetics.types.balloon;

import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.properties.Equippable;
import com.minehut.cosmetics.cosmetics.properties.Tickable;
import com.minehut.cosmetics.util.EntityUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public abstract class BalloonCosmetic extends Cosmetic implements Equippable, Tickable {

    private boolean equipped = false;

    private UUID balloon;
    private UUID string;

    /**
     * Class for creating "mounted companions", these are
     * just item stacks that are equipped on an armor stands head
     * and always face the player
     *
     * @param id of the cosmetic
     */
    public BalloonCosmetic(String id) {
        super(id, CosmeticCategory.BALLOON);
    }

    @Override
    public void equip() {
        final Player player = player().orElse(null);

        if (equipped || player == null) {
            return;
        }
        this.referenceLocation = player.getLocation();

        this.equipped = true;
        final ArmorStand balloon = EntityUtil.spawnCosmeticEntity(player.getLocation(), ArmorStand.class, stand -> {
            stand.setBasePlate(false);
            stand.setVisible(false);
            stand.setInvisible(true);
            stand.setCanPickupItems(false);
            stand.setGravity(false);
            stand.setMarker(true);
            stand.setCollidable(false);
            stand.getEquipment().setHelmet(menuIcon());
            stand.setInvulnerable(true);
        });

        final Chicken string = EntityUtil.spawnCosmeticEntity(player.getLocation(), Chicken.class, (chicken) -> {
            chicken.setInvulnerable(true);
            chicken.setInvisible(true);
            chicken.setSilent(true);
            chicken.setAware(false);
            chicken.setCollidable(false);
            chicken.setLeashHolder(player);
            chicken.setAgeLock(true);
            chicken.setBaby();
            chicken.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, Integer.MAX_VALUE, 5, false, false, false));
        });

        this.balloon = balloon.getUniqueId();
        this.string = string.getUniqueId();
    }

    @Override
    public void unequip() {
        if (!equipped) {
            return;
        }
        this.equipped = false;

        balloon().ifPresent(Entity::remove);
        string().ifPresent(Entity::remove);
    }

    private static final long ROTATION_CYCLE_DURATION = 20;

    private static final long BOB_CYCLE_DURATION = 60L;
    private static final float MAX_BOB = .5f;
    private float targetYaw = 0;
    private Location referenceLocation;
    private long rotationTick = 0;
    private long bobTick = 0;

    @Override
    public void tick() {
        player().ifPresent(player -> string().ifPresent(string -> balloon().ifPresent(balloon -> {
            Location location = player.getLocation();
            location.setYaw(referenceLocation.getYaw());
            if (rotationTick > ROTATION_CYCLE_DURATION) {
                this.targetYaw = ThreadLocalRandom.current().nextInt(10) - 5;
                this.rotationTick = 0;
            }

            if (bobTick > BOB_CYCLE_DURATION) {
                this.bobTick = 0;
            }

            if (this.targetYaw > location.getYaw()) {
                location.setYaw(location.getYaw() + 0.2f);
            } else if (this.targetYaw < location.getYaw()) {
                location.setYaw(location.getYaw() - 0.2f);
            }

            Location targetLocation = balloon.getLocation().subtract(0f, 3f, 0f).clone();
            if (bobTick <= BOB_CYCLE_DURATION / 2) {
                location.subtract(0, (bobTick / (float) BOB_CYCLE_DURATION) * MAX_BOB, 0);
            } else {
                location.subtract(0, MAX_BOB - ((bobTick / (float) BOB_CYCLE_DURATION) * MAX_BOB), 0);
            }

            Vector vector = location.toVector().subtract(targetLocation.toVector());
            vector.multiply(.3f);
            targetLocation.add(vector);
            double x = Math.toRadians(vector.getZ() * 50D * -1D);
            double y = Math.toRadians(location.getYaw());
            double z = Math.toRadians(vector.getX() * 50D * -1D);
            balloon.setHeadPose(new EulerAngle(x, y, z));

            balloon.teleport(location.add(0f, 2f, 0f));
            string.teleport(location.add(0f, 1.2f, 0f));

            this.referenceLocation = player.getLocation();
            this.referenceLocation.setYaw(location.getYaw());

            this.rotationTick++;
            this.bobTick++;
        })));

    }

    public Optional<ArmorStand> balloon() {
        if (Bukkit.getEntity(balloon) instanceof ArmorStand balloonEntity) {
            return Optional.of(balloonEntity);
        }
        return Optional.empty();
    }

    public Optional<Chicken> string() {
        if (Bukkit.getEntity(string) instanceof Chicken stringEntity) {
            return Optional.of(stringEntity);
        }
        return Optional.empty();
    }
}
