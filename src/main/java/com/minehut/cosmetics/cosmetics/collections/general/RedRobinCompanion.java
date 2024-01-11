package com.minehut.cosmetics.cosmetics.collections.general;


import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.properties.Animation;
import com.minehut.cosmetics.cosmetics.types.companion.CompanionCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RedRobinCompanion extends CompanionCosmetic {
    public RedRobinCompanion() {
        super(
                com.minehut.cosmetics.cosmetics.types.companion.Companion.RED_ROBIN.name(),
                new Vector(0, -1, 0),
                true,
                true,
                false
        );
    }

    private final Animation animation = new Animation(
            List.of(
                    Model.Companion.RED_ROBIN.FRAME_1,
                    Model.Companion.RED_ROBIN.FRAME_2,
                    Model.Companion.RED_ROBIN.FRAME_3,
                    Model.Companion.RED_ROBIN.FRAME_4,
                    Model.Companion.RED_ROBIN.FRAME_5,
                    Model.Companion.RED_ROBIN.FRAME_6,
                    Model.Companion.RED_ROBIN.FRAME_5,
                    Model.Companion.RED_ROBIN.FRAME_4,
                    Model.Companion.RED_ROBIN.FRAME_3,
                    Model.Companion.RED_ROBIN.FRAME_2
            )
    );

    private long lastTick = 0;

    @Override
    public void tick() {
        super.tick();
        long currentTick = Bukkit.getCurrentTick();
        if ((currentTick - lastTick) < 2) return;

        for (LivingEntity entity : entities()) {
            final var equipment = entity.getEquipment();
            if (equipment == null) continue;
            final ItemStack helmet = equipment.getHelmet();
            if (helmet == null) continue;
            helmet.editMeta(meta -> meta.setCustomModelData(animation.getNextFrame()));
            equipment.setHelmet(helmet);
        }

        lastTick = currentTick;
    }

    @Override
    public Permission permission() {
        return Permission.hasPurchased(this);
    }

    @Override
    public Permission visibility() {
        return Permission.deny();
    }

    @Override
    public Component name() {
        return Component.text("Yumm")
                .decoration(TextDecoration.ITALIC, false)
                .color(rarity().display().color());
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.SCUTE)
                .display(name())
                .modelData(Model.Companion.RED_ROBIN.FRAME_1)
                .build();
    }

    @Override
    public @NotNull Collection collection() {
        return Collection.GENERAL;
    }
}
