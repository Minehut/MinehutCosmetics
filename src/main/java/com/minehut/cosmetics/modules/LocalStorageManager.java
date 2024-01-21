package com.minehut.cosmetics.modules;

import com.minehut.cosmetics.model.profile.LocalCosmeticProfile;
import com.minehut.cosmetics.model.request.CosmeticState;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class LocalStorageManager {

    private final ConcurrentHashMap<UUID, LocalCosmeticProfile> profiles = new ConcurrentHashMap<>();
    private final Plugin plugin;

    public LocalStorageManager(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Load the cosmetic profile for the user w/ the given UUID from disk.
     * If this is called after a profile has already been loaded for this user, the
     * newly loaded
     * profile will overwrite the old one.
     * <p>
     * After a profile is loaded it can be retrieved using {@link #getProfile(UUID)}
     *
     * @param uuid of the user to load the profile for
     * @return future containing the loaded cosmetic profile
     */
    public CompletableFuture<LocalCosmeticProfile> loadProfile(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            LocalCosmeticProfile profile = null;
            try {
                Path profilePath = createPath(uuid);
                YamlConfiguration yamlProfile = YamlConfiguration.loadConfiguration(profilePath.toFile());
                profile = yamlProfile.getObject("profile", LocalCosmeticProfile.class);
            } catch (Exception ignored) {
                // ignored
            }

            // handle null cases if it ever occurs (someone messes up a profile file)
            if (profile == null) {
                profile = new LocalCosmeticProfile();
            }

            profiles.put(uuid, profile);
            return profile;
        });
    }

    public void writeProfile(UUID uuid, CosmeticState state) {
        CompletableFuture.runAsync(() -> {
            final LocalCosmeticProfile profile = new LocalCosmeticProfile(state);
            final Path path = createPath(uuid);

            try {
                // try to create parent directories
                if (!Files.exists(path)) {
                    Files.createDirectories(path.getParent());
                    Files.createFile(path);
                }

                final YamlConfiguration yaml = new YamlConfiguration();
                yaml.set("profile", profile);
                Files.writeString(path, yaml.saveToString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Retrieve the users cosmetic profile from the cache. You must first call
     * {@link #loadProfile(UUID)} for this user before retrieving their profile
     * here.
     *
     * @param uuid of the user to load the profile for
     * @return the loaded profile for that user
     */
    public Optional<LocalCosmeticProfile> getProfile(UUID uuid) {
        return Optional.ofNullable(profiles.get(uuid));
    }

    /**
     * 'Unloads' a profile by removing it from the local profile cache, if you wish
     * to
     * once again retrieve this users profile you must load it through
     * {@link #loadProfile(UUID)}
     *
     * @param uuid of the user to remove a profile for
     */
    public void unloadProfile(UUID uuid) {
        profiles.remove(uuid);
    }

    private Path createPath(UUID uuid) {
        return Path.of(plugin.getDataFolder().toString(), "profiles", uuid.toString() + ".yml");
    }
}
