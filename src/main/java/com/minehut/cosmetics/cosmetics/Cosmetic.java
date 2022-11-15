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
    private final Component name;
    protected UUID owner;
    private final CosmeticCategory type;
    private final String id;

    protected Cosmetic(final String id,
                       final CosmeticCategory type,
                       final Component name) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public abstract Permission permission();

    public abstract Permission visibility();

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

    public Component name() {
        return name;
    }

    /**
     * Gets the ID of the cosmetic used for deserializing, combines the
     * category and id in the format CATEGORY:ID,
     * the cosmetic can be retrieved from this string using
     * {@link CosmeticCategory#cosmetic(String)}
     */
    public String getQualifiedId() {
        return category().name() + ":" + id();
    }

    public abstract @NotNull ItemStack menuIcon();


    /**
     * Get a cosmetic using a category string and id string,
     * this is primarily useful for grabbing cosmetic values in game
     * from the "equipped" field of the cosmetic profile
     *
     * @param category of the cosmetic
     * @param id       of the cosmetic
     * @return a possible cosmetic for the given category and id
     */
    public static Optional<Cosmetic> fromCategoryId(String category, String id) {
        return EnumUtil
                .valueOfSafe(CosmeticCategory.class, category.toUpperCase())
                .flatMap((cat) -> cat.cosmetic(id.toUpperCase()));
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
