package com.minehut.cosmetics.config;

import com.minehut.cosmetics.util.EnumUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class Config {

    private String apiUrl;
    private String apiSecret;
    private Mode mode;
    private Location crateLocation = null;

    private boolean hideEntitiesWithoutPack = true;
    private boolean sendResourcePack = true;

    private final Plugin plugin;

    public Config(Plugin plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        reload();
    }

    public void load() {
        final FileConfiguration pluginConfig = plugin.getConfig();

        // load the mode we're using for the plugin
        this.mode = EnumUtil.valueOfSafe(Mode.class, pluginConfig.getString("mode")).orElse(Mode.PLAYER_SERVER);
        this.hideEntitiesWithoutPack = pluginConfig.getBoolean("hide-entities-without-pack", false);
        this.sendResourcePack = pluginConfig.getBoolean("send-resource-pack", true);


        switch (mode) {
            // if we're using the lobby, try to load the socket auth config as well
            case LOBBY -> {
                final YamlConfiguration lobbyConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder().getParent(), "Lobby/socketauth.yml"));
                this.apiUrl = lobbyConfig.getString("api.url", "https://api.minehut.com");
                this.apiSecret = lobbyConfig.getString("api.auth", "nicetry");

                World world = Bukkit.getWorld(pluginConfig.getString("crateLocation.name", "lobby"));
                double x = pluginConfig.getDouble("crateLocation.x", 0);
                double y = pluginConfig.getDouble("crateLocation.y", 0);
                double z = pluginConfig.getDouble("crateLocation.z", 0);
                float yaw = (float) pluginConfig.getDouble("crateLocation.yaw", 0);

                this.crateLocation = new Location(world, x, y, z, yaw, 0);
            }
            case PLAYER_SERVER -> {
                this.apiUrl = pluginConfig.getString("apiUrl", "https://api.minehut.com");
            }
        }
    }

    /**
     * Get the operating mode for the server, if the configuration is malformed, falls
     * back to using {@link com.minehut.cosmetics.config.Mode#PLAYER_SERVER}
     *
     * @return the operating mode to use for the plugin
     */
    public Mode mode() {
        return mode;
    }

    /**
     * Return the URL endpoint for fetching cosmetic information.
     *
     * @return the api url
     */
    public String apiUrl() {
        return apiUrl;
    }

    public String apiSecret() {
        return apiSecret;
    }

    public Location crateLocation() {
        return crateLocation;
    }


    public boolean hideEntitiesWithoutPack() {
        return hideEntitiesWithoutPack;
    }

    public boolean sendResourcePack() {
        return sendResourcePack;
    }

    public void reload() {
        plugin.reloadConfig();
        load();
    }
}
