package com.minehut.cosmetics.cosmetics.collections.cheeseheads;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.types.companion.Companion;
import com.minehut.cosmetics.cosmetics.types.companion.CompanionCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public class CheeseHorseCompanion extends CompanionCosmetic {

    private static final Sound SOUND = Sound.sound(Key.key("entity.horse.ambient"), Sound.Source.AMBIENT, .5f, 1.15f);
    private static final long MAX_SOUND_TICKS = 20 * 60 * 3;
    private static final long MIN_SOUND_TICKS = (long) (20 * 60 * 1.5);

    private long nextTick = 0;

    public CheeseHorseCompanion() {
        super(Companion.CHEESE_HORSE.name(),
            new Vector(0, 0, 0),
            true,
            true,
            false);

        updateSoundTicks();
    }

    @Override
    public Component name() {
        return Component.text("Cheese Horse", NamedTextColor.GOLD);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        ItemStack item = new ItemStack(Material.SCUTE);
        item.editMeta(meta -> {
            meta.displayName(name());
            meta.setCustomModelData(Model.Companion.CHEESE_HORSE);
        });
        return item;
    }

    @Override
    public void tick() {
        super.tick();

        if (Bukkit.getCurrentTick() >= nextTick) {

            for (LivingEntity entity : entities()) {
                Location location = entity.getLocation();
                entity.getWorld().playSound(SOUND, location.getX(), location.getY(), location.getZ());
            }

            updateSoundTicks();
        }
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.CHEESE_HEADS;
    }

    private void updateSoundTicks() {
        this.nextTick = Bukkit.getCurrentTick() + MIN_SOUND_TICKS + ThreadLocalRandom.current().nextLong(MAX_SOUND_TICKS - MIN_SOUND_TICKS);
    }
}
