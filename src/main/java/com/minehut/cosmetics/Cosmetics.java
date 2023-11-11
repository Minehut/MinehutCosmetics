package com.minehut.cosmetics;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.minehut.cosmetics.api.PluginApi;
import com.minehut.cosmetics.commands.EquipCommand;
import com.minehut.cosmetics.commands.MenuCommand;
import com.minehut.cosmetics.commands.SkinCommand;
import com.minehut.cosmetics.commands.UnSkinCommand;
import com.minehut.cosmetics.commands.debug.Debug;
import com.minehut.cosmetics.commands.debug.GiveCosmetic;
import com.minehut.cosmetics.config.Config;
import com.minehut.cosmetics.config.Mode;
import com.minehut.cosmetics.cosmetics.CosmeticsManager;
import com.minehut.cosmetics.cosmetics.crates.CratesModule;
import com.minehut.cosmetics.cosmetics.entities.EntityHandler;
import com.minehut.cosmetics.cosmetics.listeners.CosmeticEntityListener;
import com.minehut.cosmetics.cosmetics.listeners.CosmeticsListener;
import com.minehut.cosmetics.cosmetics.listeners.CosmeticsTeleportListener;
import com.minehut.cosmetics.cosmetics.listeners.DeathListener;
import com.minehut.cosmetics.cosmetics.listeners.EmojiListener;
import com.minehut.cosmetics.cosmetics.listeners.LeashListener;
import com.minehut.cosmetics.cosmetics.listeners.ResourcePackListener;
import com.minehut.cosmetics.cosmetics.listeners.skins.SkinDurabilityListener;
import com.minehut.cosmetics.cosmetics.listeners.skins.SkinEquipListener;
import com.minehut.cosmetics.cosmetics.listeners.skins.SkinModifyListener;
import com.minehut.cosmetics.cosmetics.listeners.skins.SkinTriggerListener;
import com.minehut.cosmetics.cosmetics.listeners.trinkets.IceStaffListener;
import com.minehut.cosmetics.cosmetics.listeners.visibility.VisibilityHandler;
import com.minehut.cosmetics.cosmetics.types.balloon.Balloon;
import com.minehut.cosmetics.cosmetics.types.trinket.listener.TrinketListener;
import com.minehut.cosmetics.modules.LocalStorageManager;
import com.minehut.cosmetics.modules.polling.RankPollingModule;
import com.minehut.cosmetics.modules.polling.ResourcePackPollingModule;
import com.minehut.cosmetics.network.ExternalAPI;
import com.minehut.cosmetics.network.InternalAPI;
import com.minehut.cosmetics.network.NetworkApi;
import com.minehut.cosmetics.pack.ResourcePackManager;
import com.minehut.cosmetics.util.EntityUtil;
import com.minehut.cosmetics.util.data.Key;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
    private ResourcePackPollingModule packModule;
    private EntityHandler entityHandler;
    private VisibilityHandler visibilityHandler;
    private ResourcePackManager resourcePackManager;
    private PluginApi pluginApi;

    // api for accessing remote services
    private NetworkApi api;

    // Polling Modules
    @Override
    public void onEnable() {
        Cosmetics.INSTANCE = this;
        Key.init(this);

        this.gson = buildGson();
        this.config = new Config(this);
        this.manager = new CosmeticsManager(this);
        this.entityHandler = new EntityHandler();
        this.visibilityHandler = new VisibilityHandler(this);
        this.resourcePackManager = new ResourcePackManager();
        this.pluginApi = new PluginApi(this);

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
        this.packModule = new ResourcePackPollingModule(this);
        packModule.init();
        RankPollingModule rankPollingModule = new RankPollingModule(this);
        rankPollingModule.init();

        getLogger().info("Using operation mode '%s'!".formatted(config.mode()));

        registerEvents(new CosmeticsListener(this));
        registerEvents(new ResourcePackListener(packModule));
        registerEvents(new DeathListener(this));
        registerEvents(new LeashListener());
        registerEvents(new CosmeticsTeleportListener(this));
        registerEvents(new CosmeticEntityListener());
        registerEvents(new TrinketListener());
        registerEvents(new EmojiListener());
        registerEvents(resourcePackManager);
        registerEvents(entityHandler);

        // manage visibility events
        if (config.hideEntitiesWithoutPack()) {
            registerEvents(visibilityHandler);
        }

        // trinket listeners
        registerEvents(new IceStaffListener());

        // register commands
        new MenuCommand().register(this);
        new EquipCommand(this, manager).register(this);
        new Debug().register(this);
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

    public LocalStorageManager localStorage() {
        if (Mode.PLAYER_SERVER != config().mode()) {
            throw new IllegalStateException("Must be in player server mode to access local storage");
        }
        return localStorage;
    }

    public CosmeticsManager manager() {
        return manager;
    }

    public Config config() {
        return config;
    }

    public NetworkApi networkApi() {
        return api;
    }

    public PluginApi getPluginApi() {
        return pluginApi;
    }

    public CratesModule crates() {
        return crates;
    }

    public EntityHandler entityHandler() {
        return entityHandler;
    }

    public ResourcePackPollingModule packModule() {
        return packModule;
    }

    public VisibilityHandler visibilityHandler() {
        return visibilityHandler;
    }

    public static Mode mode() {
        return get().config().mode();
    }

    /**
     * Remove any entities that ar marked w/ the cosmetic key
     */
    private void removeCosmeticEntities() {
        Bukkit.getWorlds().forEach(world -> world.getEntities().forEach(entity -> {
            if (!EntityUtil.isCosmeticEntity(entity)) {
                return;
            }
            entity.remove();
        }));
    }

    public ResourcePackManager resourcePackManager() {
        return resourcePackManager;
    }

    public static Cosmetics get() {
        return INSTANCE;
    }
}
