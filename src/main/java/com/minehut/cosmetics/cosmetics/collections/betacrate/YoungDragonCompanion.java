package com.minehut.cosmetics.cosmetics.collections.betacrate;

import com.minehut.cosmetics.cosmetics.Collection;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.Rarity;
import com.minehut.cosmetics.cosmetics.properties.Animation;
import com.minehut.cosmetics.cosmetics.types.companion.Companion;
import com.minehut.cosmetics.cosmetics.types.companion.CompanionCosmetic;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

public class YoungDragonCompanion extends CompanionCosmetic {

    public YoungDragonCompanion() {
        super(Companion.YOUNG_DRAGON.name(),
                new Vector(0, -1, 0),
                true,
                true,
                false
        );
    }

    @Override
    public Component name() {
        return Component.text("Young Dragon")
                .color(rarity().display().color())
                .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public @NotNull ItemStack menuIcon() {
        return ItemBuilder.of(Material.SCUTE)
                .display(name())
                .modelData(Model.Companion.YOUNG_DRAGON.FRAME_2)
                .build();
    }

    private final Animation animation = new Animation(
            List.of(
                    Model.Companion.YOUNG_DRAGON.FRAME_1,
                    Model.Companion.YOUNG_DRAGON.FRAME_2,
                    Model.Companion.YOUNG_DRAGON.FRAME_3,
                    Model.Companion.YOUNG_DRAGON.FRAME_4,
                    Model.Companion.YOUNG_DRAGON.FRAME_3,
                    Model.Companion.YOUNG_DRAGON.FRAME_2
            )
    );

    // TODO: Add animated companion
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
    public @NotNull Collection collection() {
        return Collection.DRAGON_CRATE;
    }

    @Override
    public @NotNull Rarity rarity() {
        return Rarity.LEGENDARY;
    }
}
