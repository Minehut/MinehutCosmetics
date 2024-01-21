package com.minehut.cosmetics.cosmetics;

import com.minehut.cosmetics.util.EnumUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public abstract class Cosmetic {
    protected UUID owner;
    private final CosmeticCategory type;
    private final String id;

    protected Cosmetic(final String id,
                       final CosmeticCategory type) {
        this.id = id;
        this.type = type;
    }

    public Permission permission() {
        return Permission.hasPurchased(category(), id());
    }

    public Permission visibility() {
        return Permission.collectionIsActive(collection());
    }

    public Optional<UUID> owner() {
        return Optional.ofNullable(owner);
    }

    public void owner(final UUID owner) {
        this.owner = owner;
    }

    public Optional<Player> player() {
        return owner().map(Bukkit::getPlayer).filter(Player::isOnline);
    }

    public CosmeticCategory category() {
        return type;
    }

    public String id() {
        return id;
    }

    public abstract Component name();

    /**
     * Gets the ID of the cosmetic used for deserializing, combines the
     * slot and id in the format CATEGORY:ID,
     * the cosmetic can be retrieved from this string using
     * {@link CosmeticCategory#cosmetic(String)}
     */
    public String getQualifiedId() {
        return category().name() + ":" + id();
    }

    public abstract @NotNull ItemStack menuIcon();

    /**
     * Get the cosmetic collection for this item
     *
     * @return the collection
     */
    public abstract @NotNull Collection collection();

    public @NotNull Rarity rarity() {
        return Rarity.UNCOMMON;
    }

    public int salvageAmount() {
        return rarity().salvageValue();
    }


    /**
     * Get a cosmetic using a slot string and id string,
     * this is primarily useful for grabbing cosmetic values in game
     * from the "equipped" field of the cosmetic profile
     *
     * @param category of the cosmetic
     * @param id       of the cosmetic
     * @return a possible cosmetic for the given slot and id
     */
    public static Optional<Cosmetic> fromCategoryId(String category, String id) {
        return EnumUtil
                .valueOfSafe(CosmeticCategory.class, category.toUpperCase())
                .flatMap(cat -> cat.cosmetic(id.toUpperCase()));
    }

    /**
     * Grab a cosmetic given a "qualified id",
     * qualified ids are in the format CATEGORY:COSMETIC
     */
    public static Optional<Cosmetic> fromQualifiedId(String query) {
        final String[] chunks = query.split(":");
        if (chunks.length < 2) return Optional.empty();

        final String category = chunks[0];
        final String id = chunks[1];

        return fromCategoryId(category, id);
    }
}
