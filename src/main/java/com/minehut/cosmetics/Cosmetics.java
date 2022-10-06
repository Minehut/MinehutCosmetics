package com.minehut.cosmetics;

import com.minehut.cosmetics.commands.Debug;
import com.minehut.cosmetics.commands.MenuCommand;
import com.minehut.cosmetics.commands.SkinCommand;
import com.minehut.cosmetics.commands.UnSkinCommand;
import com.minehut.cosmetics.config.ConfigManager;
import com.minehut.cosmetics.config.Mode;
import com.minehut.cosmetics.cosmetics.CosmeticsManager;
import com.minehut.cosmetics.events.CosmeticEntityEvent;
import com.minehut.cosmetics.events.CosmeticsListener;
import com.minehut.cosmetics.events.DeathListener;
import com.minehut.cosmetics.events.LeashEvent;
import com.minehut.cosmetics.events.ResourcePackListener;
import com.minehut.cosmetics.events.CosmeticsTeleportEvent;
import com.minehut.cosmetics.events.skins.SkinDurabilityListener;
import com.minehut.cosmetics.events.skins.SkinEquipListener;
import com.minehut.cosmetics.events.skins.SkinModifyListener;
import com.minehut.cosmetics.events.skins.SkinTriggerListener;
import com.minehut.cosmetics.modules.KeyManager;
import com.minehut.cosmetics.modules.LocalStorageManager;
import com.minehut.cosmetics.modules.polling.RankPollingModule;
import com.minehut.cosmetics.modules.polling.ResourcePackPollingModule;
import com.minehut.cosmetics.network.CosmeticsAPI;
import com.minehut.cosmetics.network.ExternalAPI;
import com.minehut.cosmetics.network.InternalAPI;
import com.minehut.cosmetics.util.EntityUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public final class Cosmetics extends JavaPlugin {

    private static Cosmetics INSTANCE;

    // Managers
    private ConfigManager config;
    private CosmeticsManager manager;
    private KeyManager keyManager;

    // Local storage only for use on player servers
    private @Nullable LocalStorageManager localStorage;

    // api for accessing remote services
    private CosmeticsAPI api;

    // Polling Modules
    @Override
    public void onEnable() {
        Cosmetics.INSTANCE = this;
        this.config = new ConfigManager(this);
        this.keyManager = new KeyManager(this);
        this.manager = new CosmeticsManager(this);

        // process different actions depending on the operation mode the server is in
        switch (config().mode()) {
            case LOBBY -> {
                this.api = new InternalAPI(config);
            }
            case PLAYER_SERVER -> {
                this.localStorage = new LocalStorageManager(this);
                this.api = new ExternalAPI(config);
                // register listeners
                registerEvents(new SkinTriggerListener(this, manager));
                registerEvents(new SkinEquipListener());
                registerEvents(new SkinDurabilityListener(keyManager));
                registerEvents(new SkinModifyListener());

                // commands
                setExecutor("skin", new SkinCommand(this, manager));
                setExecutor("unskin", new UnSkinCommand());
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
        registerEvents(new LeashEvent(this));
        registerEvents(new CosmeticsTeleportEvent(this));
        registerEvents(new CosmeticEntityEvent());

        // register commands
        setExecutor("cosmetics", new MenuCommand(manager));
        setExecutor("cosmeticsdebug", new Debug(this));
    }

    private void setExecutor(String name, CommandExecutor exec) {
        Optional.ofNullable(getCommand(name)).ifPresent((cmd) -> cmd.setExecutor(exec));
    }

    private void registerEvents(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    public CosmeticsManager cosmeticManager() {
        return manager;
    }

    public KeyManager keyManager() {
        return keyManager;
    }

    public ConfigManager config() {
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

}
