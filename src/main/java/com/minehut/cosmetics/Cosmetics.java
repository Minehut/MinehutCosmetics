package com.minehut.cosmetics;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.minehut.cosmetics.commands.debug.Debug;
import com.minehut.cosmetics.commands.MenuCommand;
import com.minehut.cosmetics.commands.SkinCommand;
import com.minehut.cosmetics.commands.UnSkinCommand;
import com.minehut.cosmetics.commands.debug.GiveCosmetic;
import com.minehut.cosmetics.config.Config;
import com.minehut.cosmetics.config.Mode;
import com.minehut.cosmetics.cosmetics.CosmeticsManager;
import com.minehut.cosmetics.cosmetics.types.trinket.listener.TrinketListener;
import com.minehut.cosmetics.crates.CratesModule;
import com.minehut.cosmetics.events.CosmeticsListener;
import com.minehut.cosmetics.events.CosmeticsTeleportListener;
import com.minehut.cosmetics.events.DeathListener;
import com.minehut.cosmetics.events.LeashListener;
import com.minehut.cosmetics.events.ResourcePackListener;
import com.minehut.cosmetics.events.skins.SkinDurabilityListener;
import com.minehut.cosmetics.events.skins.SkinEquipListener;
import com.minehut.cosmetics.events.skins.SkinModifyListener;
import com.minehut.cosmetics.events.skins.SkinTriggerListener;
import com.minehut.cosmetics.listeners.CosmeticEntityListener;
import com.minehut.cosmetics.listeners.EmojiHandler;
import com.minehut.cosmetics.listeners.visibility.CosmeticsVisibilityHandler;
import com.minehut.cosmetics.modules.LocalStorageManager;
import com.minehut.cosmetics.modules.polling.RankPollingModule;
import com.minehut.cosmetics.modules.polling.ResourcePackPollingModule;
import com.minehut.cosmetics.network.CosmeticsAPI;
import com.minehut.cosmetics.network.ExternalAPI;
import com.minehut.cosmetics.network.InternalAPI;
import com.minehut.cosmetics.util.EntityUtil;
import com.minehut.cosmetics.util.data.Key;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public final class Cosmetics extends JavaPlugin {

    private static Cosmetics INSTANCE;

    // utilities
    private Config config;
    private Gson gson;

    private CosmeticsManager manager;
    // Local storage only for use on player servers
    private @Nullable LocalStorageManager localStorage;
    private @Nullable CratesModule crates;

    // api for accessing remote services
    private CosmeticsAPI api;

    // Polling Modules
    @Override
    public void onEnable() {
        Cosmetics.INSTANCE = this;
        Key.init(this);

        this.gson = buildGson();
        this.config = new Config(this);
        this.manager = new CosmeticsManager(this);

        // process different actions depending on the operation mode the server is in
        switch (config().mode()) {
            case LOBBY -> {
                this.api = new InternalAPI(config, gson);
                this.crates = new CratesModule(this);
                new GiveCosmetic().register(this);
            }
            case PLAYER_SERVER -> {
                this.localStorage = new LocalStorageManager(this);
                this.api = new ExternalAPI(config, gson);
                // register listeners
                registerEvents(new SkinTriggerListener(this, manager));
                registerEvents(new SkinEquipListener());
                registerEvents(new SkinDurabilityListener());
                registerEvents(new SkinModifyListener());

                // commands
                new SkinCommand(this).register(this);
                new UnSkinCommand().register(this);
            }
        }
        removeCosmeticEntities();

        // setup polling containers
        ResourcePackPollingModule packInfoModule = new ResourcePackPollingModule(this);
        packInfoModule.init();
        RankPollingModule rankPollingModule = new RankPollingModule(this);
        rankPollingModule.init();

        getLogger().info("Using operation mode '%s'!".formatted(config.mode()));

        registerEvents(new CosmeticsListener(this));
        registerEvents(new ResourcePackListener(this, packInfoModule));
        registerEvents(new DeathListener(this));
        registerEvents(new LeashListener(this));
        registerEvents(new CosmeticsTeleportListener(this));
        registerEvents(new CosmeticEntityListener());
        registerEvents(new TrinketListener());
        registerEvents(new EmojiHandler());
        registerEvents(new CosmeticsVisibilityHandler(cosmeticManager()), this);


        // register commands
        new MenuCommand().register(this);
        new Debug(this).register(this);
    }

    private void registerEvents(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    private Gson buildGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(TextColor.class, (JsonDeserializer<TextColor>) (element, type, context) -> {
            try {
                return NamedTextColor.NAMES.value(element.getAsJsonPrimitive().getAsString().toLowerCase(Locale.ROOT));
            } catch (IllegalArgumentException ex) {
                return NamedTextColor.WHITE;
            }
        });

        return builder.create();
    }

    public CosmeticsManager cosmeticManager() {
        return manager;
    }

    public Config config() {
        return config;
    }

    public LocalStorageManager localStorage() {
        if (Mode.PLAYER_SERVER != config().mode()) {
            throw new IllegalStateException("Must be in player server mode to access local storage");
        }
        return localStorage;
    }

    public CosmeticsAPI api() {
        return api;
    }

    public CratesModule crates() {
        return crates;
    }

    public static Mode mode() {
        return get().config().mode();
    }

    /**
     * Remove any entities that ar marked w/ the cosmetic key
     */
    private void removeCosmeticEntities() {
        Bukkit.getWorlds().forEach(world -> world.getEntities().forEach(entity -> {
            if (!EntityUtil.isCosmeticEntity(entity)) return;
            entity.remove();
        }));
    }


    public static Cosmetics get() {
        return INSTANCE;
    }

    public Gson gson() {
        return gson;
    }
}
