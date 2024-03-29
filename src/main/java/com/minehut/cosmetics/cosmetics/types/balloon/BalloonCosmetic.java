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

import java.util.Optional;
import java.util.UUID;

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

    private static final long BOB_CYCLE_DURATION = 60L;
    private static final float MAX_BOB = .5f;
    private long bobTick = 0;

    @Override
    public void tick() {
        player().ifPresent(player -> string().ifPresent(string -> balloon().ifPresent(balloon -> {
            Location location = player.getLocation();
            location.setYaw(0);

            if (bobTick > BOB_CYCLE_DURATION) {
                this.bobTick = 0;
            }

            if (bobTick <= BOB_CYCLE_DURATION / 2) {
                location.subtract(0, (bobTick / (float) BOB_CYCLE_DURATION) * MAX_BOB, 0);
            } else {
                location.subtract(0, MAX_BOB - ((bobTick / (float) BOB_CYCLE_DURATION) * MAX_BOB), 0);
            }

            double angle = -Math.toRadians((player.getEyeLocation().getYaw() + 360) % 360);
            double xOff = -Math.sin(angle) * 1.5;
            double zOff = -Math.cos(angle);

            balloon.teleport(location.add(xOff, 2f, zOff));
            string.teleport(balloon.getLocation().clone().add(0, 1.8, 0));

            final Location dir = player.getEyeLocation().subtract(balloon.getEyeLocation());
            double eulerX = -(Math.PI - Math.atan2(Math.hypot(dir.getX(), dir.getZ()), dir.getY())) + Math.PI / 2;
            double eulerY = -(Math.PI - Math.atan2(dir.getZ(), dir.getX())) + Math.PI / 2;
            balloon.setHeadPose(new EulerAngle(eulerX, eulerY, 0));

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
