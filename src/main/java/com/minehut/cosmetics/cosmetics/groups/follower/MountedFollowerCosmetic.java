package com.minehut.cosmetics.cosmetics.groups.follower;

import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.util.EntityUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.function.Function;

public abstract class MountedFollowerCosmetic extends FollowerCosmetic {
    private final boolean small;

    private final boolean lookX;
    private final boolean lookY;

    /**
     * Class for creating "mounted companions", these are
     * just itemstacks that are equipped on an armor stands head
     * and always face the player
     *
     * @param id                of the cosmetic
     * @param category          the category belongs to
     * @param name              of this companion
     * @param permission        required to equip this companion
     * @param visibility        permission to see this item
     * @param companionSupplier build the itemstack for this companion
     * @param offset            to spawn the cosmetic at
     * @param small             whether to use a mini armor stand
     * @param lookX             whether to track rotation on the x axis
     * @param lookY             whether to track rotation on the y axis
     */
    public MountedFollowerCosmetic(
            final String id,
            final CosmeticCategory category,
            final Component name,
            final Permission permission,
            final Permission visibility,
            final Function<Player, ItemStack> companionSupplier,
            final Vector offset,
            final boolean small,
            final boolean lookX,
            final boolean lookY
    ) {
        this(id, category, name, permission, visibility, List.of(companionSupplier), offset, small, lookX, lookY);
    }

    public MountedFollowerCosmetic(
            final String id,
            final CosmeticCategory category,
            final Component name,
            final Permission permission,
            final Permission visibility,
            final List<Function<Player, ItemStack>> companionSuppliers,
            final Vector offset,
            final boolean small,
            final boolean lookX,
            final boolean lookY
    ) {
        super(id, category, name, permission, visibility, companionSuppliers, offset);
        this.small = small;
        this.lookX = lookX;
        this.lookY = lookY;
    }

    @Override
    public Entity spawnEntity(Player player, Location location, Function<Player, ItemStack> companionSupplier) {
        Location spawnLocation = location.clone();
        spawnLocation.setYaw(0);
        spawnLocation.setPitch(0);
        spawnLocation.subtract(0, 1.5, 0);

        return EntityUtil.spawnModelStand(spawnLocation, stand -> {
            stand.setItem(EquipmentSlot.HEAD, companionSupplier.apply(player));
            stand.setSmall(small);
        });
    }

    @Override
    public void tick() {
        super.tick();

        // if we aren't tracking in either direction ignore it
        if (!lookX && !lookY) return;

        // make the pet 'look' at the player
        player().ifPresent(player -> entities().forEach((entity) -> {
            ArmorStand stand = (ArmorStand) entity;
            Location dir = player.getEyeLocation().subtract(stand.getEyeLocation());

            double x = dir.getX();
            double y = dir.getY();
            double z = dir.getZ();

            double eulerX = lookY ? -(Math.PI - Math.atan2(Math.hypot(x, z), y)) + Math.PI / 2 : 0f;
            double eulerY = lookX ? -(Math.PI - Math.atan2(z, x)) + Math.PI / 2 : 0f;
            EulerAngle angle = new EulerAngle(
                    eulerX,
                    eulerY,
                    0
            );
            stand.setHeadPose(angle);
        }));
    }

    @Override
    public List<LivingEntity> entities() {
        return super.entities().stream().filter(LivingEntity.class::isInstance).map(LivingEntity.class::cast).toList();
    }
}