package com.minehut.cosmetics.cosmetics.groups.wing;

import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.properties.Equippable;
import com.minehut.cosmetics.cosmetics.properties.Tickable;
import com.minehut.cosmetics.util.EntityUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public abstract class WingCosmetic extends Cosmetic implements Equippable, Tickable {

    private final Function<Player, ItemStack> wingSupplier;
    private ArmorStand wings;
    private Entity base;

    private boolean equipped = false;

    protected WingCosmetic(String id,
                           Component name,
                           Function<Player, CompletableFuture<Boolean>> permission,
                           Function<Player, ItemStack> wingSupplier) {
        super(id, name, permission, CosmeticCategory.WING);
        this.wingSupplier = wingSupplier;
    }

    @Override
    public void equip() {
        if (equipped) return;
        equipped = true;
        player().ifPresent(player -> {
            final Location spawn = player.getLocation();
            spawn.setYaw(0);
            spawn.setPitch(0);
            this.wings = EntityUtil.spawnModelStand(player.getLocation(), wings -> {
                wings.setItem(EquipmentSlot.HEAD, wingSupplier.apply(player));
                wings.setVisible(true);
            });

            this.base = EntityUtil.spawnCosmeticEntity(player.getLocation(), AreaEffectCloud.class, cloud -> {
                cloud.clearCustomEffects();
                cloud.setRadius(0);
                cloud.setParticle(Particle.BLOCK_CRACK, Bukkit.createBlockData(Material.AIR));
                cloud.setTicksLived(Integer.MAX_VALUE);
            });

            base.addPassenger(wings);
            player.addPassenger(base);
        });
    }

    @Override
    public void unequip() {
        if (!equipped) return;
        equipped = false;
        wings().ifPresent(Entity::remove);
        base().ifPresent(Entity::remove);
    }

    @Override
    public void tick() {
        player().ifPresent(player -> wings().ifPresent(wings -> {
            wings.setRotation(player.getEyeLocation().getYaw(), 0);
        }));
    }

    public Optional<ArmorStand> wings() {
        return Optional.ofNullable(wings);
    }

    public Optional<Entity> base() {
        return Optional.ofNullable(base);
    }
}
