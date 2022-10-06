package com.minehut.cosmetics.config;

import com.minehut.cosmetics.util.EnumUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Optional;

public class ConfigManager {

    private String apiUrl = "https://api.minehut.com";
    private String apiSecret = "";
    private Mode mode = Mode.PLAYER_SERVER;


    private final Plugin plugin;

    public ConfigManager(Plugin plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        reload();
    }

    public void load() {
        final FileConfiguration pluginConfig = plugin.getConfig();

        // load the mode we're using for the plugin
        EnumUtil.valueOfSafe(Mode.class, pluginConfig.getString("mode")).ifPresent(newMode -> this.mode = newMode);

        switch (mode) {
            // if we're using the lobby, try to load the socket auth config as well
            case LOBBY -> {
                final YamlConfiguration lobbyConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder().getParent(), "Lobby/socketauth.yml"));
                Optional.ofNullable(lobbyConfig.getString("api.url")).ifPresent(url -> this.apiUrl = url);
                Optional.ofNullable(lobbyConfig.getString("api.auth")).ifPresent(secret -> this.apiSecret = secret);
            }
            case PLAYER_SERVER -> {
                Optional.ofNullable(pluginConfig.getString("apiUrl")).ifPresent(newUrl -> this.apiUrl = newUrl);
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

    public String endpoint(String route) {
        return apiUrl() + route;
    }

    public String apiSecret() {
        return apiSecret;
    }

    public void reload() {
        plugin.reloadConfig();
        load();
    }
}
