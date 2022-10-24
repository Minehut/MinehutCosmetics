package com.minehut.cosmetics.cosmetics.groups.item;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.config.Mode;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.equipment.CosmeticSlot;
import com.minehut.cosmetics.cosmetics.properties.Equippable;
import com.minehut.cosmetics.cosmetics.properties.Skinnable;
import com.minehut.cosmetics.cosmetics.properties.SlotHandler;
import com.minehut.cosmetics.util.ItemBuilder;
import com.minehut.cosmetics.util.SkinUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public abstract class ItemCosmetic extends Cosmetic implements Equippable, Skinnable, SlotHandler {
    private boolean equipped = false;

    private @Nullable CosmeticSlot slot = null;

    protected ItemCosmetic(String id, final Component name, final Function<Player, CompletableFuture<Boolean>> permission) {
        super(id, name, permission, CosmeticCategory.ITEM);
    }

    @Override
    public void equip() {
        if (equipped) return;
        equipped = true;
        player().ifPresent(player -> slot().ifPresent(slot -> {

            switch (slot) {
                case MAIN_HAND -> {
                    player.getInventory().setItem(6, item());
                    player.getInventory().setHeldItemSlot(6);
                }
                case OFF_HAND -> player.getInventory().setItemInOffHand(item());
            }
        }));
    }

    @Override
    public void unequip() {
        if (!equipped) return;
        equipped = false;

        player().ifPresent(player -> slot().ifPresent(slot -> {
            switch (slot) {
                case MAIN_HAND -> player.getInventory().setItem(6, null);
                case OFF_HAND -> player.getInventory().setItemInOffHand(null);
            }
        }));
    }

    public abstract ItemStack item();

    @Override
    public ItemStack menuIcon() {
        final ItemBuilder item = ItemBuilder.of(item());

        if (Mode.LOBBY == Cosmetics.mode()) {
            item.appendLore(
                    List.of(
                            Component.keybind("key.attack").color(NamedTextColor.YELLOW).append(Component.text(" to apply to main hand.").color(NamedTextColor.GRAY)).decoration(TextDecoration.ITALIC, false),
                            Component.keybind("key.use").color(NamedTextColor.YELLOW).append(Component.text(" to apply to off-hand.").color(NamedTextColor.GRAY)).decoration(TextDecoration.ITALIC, false)
                    )
            );
        }

        return item.build();
    }

    @Override
    public void applySkin(ItemStack item) {
        SkinUtil.writeSkinKeys(item);
        ItemStack base = item();
        item.editMeta(meta -> {
            meta.setCustomModelData(base.getItemMeta().getCustomModelData());
            SkinUtil.writeCosmeticKeys(meta, this);
        });
    }

    @Override
    public Optional<CosmeticSlot> slot() {
        return Optional.ofNullable(slot);
    }

    @Override
    public void setSlot(@Nullable CosmeticSlot slot) {
        this.slot = slot;
    }
}
