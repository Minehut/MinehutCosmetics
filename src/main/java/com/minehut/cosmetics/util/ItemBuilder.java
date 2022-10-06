package com.minehut.cosmetics.util;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ItemBuilder {

    private final ItemStack item;

    private ItemBuilder(ItemStack item) {
        this.item = item;
    }

    public ItemBuilder amount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder damage(int damage) {
        return editMeta(meta -> {
            if (!(meta instanceof Damageable damageable)) return;
            damageable.setDamage(damage);
        });
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        item.addEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder enchantUnsafe(Enchantment enchantment, int level) {
        item.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder modelData(int modelData) {
        return editMeta(meta -> meta.setCustomModelData(modelData));
    }

    public ItemBuilder display(Component display) {
        return editMeta(meta -> meta.displayName(display));
    }

    public ItemBuilder lore(List<Component> lore) {
        return editMeta(meta -> meta.lore(lore));
    }

    public ItemBuilder lore(Component... lore) {
        return lore(List.of(lore));
    }

    public ItemBuilder unbreakable(boolean unbreakable) {
        return editMeta(meta -> meta.setUnbreakable(unbreakable));
    }

    public ItemBuilder glow(boolean glow) {
        return editMeta(meta -> {
            enchantUnsafe(Enchantment.WATER_WORKER, 1);
            flags(ItemFlag.HIDE_ENCHANTS);
        });
    }

    public ItemBuilder flags(ItemFlag... flags) {
        return editMeta(meta -> meta.addItemFlags(flags));
    }

    public ItemBuilder editMeta(Consumer<ItemMeta> metaConsumer) {
        item.editMeta(metaConsumer);
        return this;
    }

    /**
     * Set the texture of skull items to the provided string
     *
     * @param texture use for the skull
     * @return the item builder
     */
    public ItemBuilder skullTexture(String texture) {
        return editMeta(meta -> {
            if (!(meta instanceof SkullMeta skullMeta)) return;
            final PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
            final Set<ProfileProperty> profileProperties = profile.getProperties();
            profileProperties.add(new ProfileProperty("textures", texture));
            profile.setProperties(profileProperties);
            skullMeta.setPlayerProfile(profile);
        });
    }

    public ItemStack build() {
        return item;
    }

    public Supplier<ItemStack> supplier() {
        return item::clone;
    }

    public static ItemBuilder of(Material type) {
        return new ItemBuilder(new ItemStack(type));
    }

    public static ItemBuilder of(ItemStack item) {
        return new ItemBuilder(item);
    }
}

