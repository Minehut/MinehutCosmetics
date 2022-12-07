package com.minehut.cosmetics.cosmetics.listeners;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.model.PackInfo;
import com.minehut.cosmetics.modules.polling.ResourcePackPollingModule;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ResourcePackListener implements Listener {

    private static final Component PACK_COPY = Component.text()
            .append(Component.text("Welcome to Minehut!").color(NamedTextColor.AQUA))
            .append(Component.newline())
            .append(Component.text("For the best experience, we recommend using our custom resource pack."))
            .append(Component.space())
            .append(Component.text("Would you like to install it automagically?"))
            .build();

    private final ResourcePackPollingModule packPollingModule;

    public ResourcePackListener(ResourcePackPollingModule packPollingModule) {
        this.packPollingModule = packPollingModule;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final PackInfo info = packPollingModule.state();
        if (!info.isValid()) return;

        final Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskAsynchronously(Cosmetics.get(), () -> {
            final boolean accepted = Cosmetics.get().resourcePackManager().fetchPackStatus(player, false).join();
            if (accepted) return;
            Bukkit.getScheduler().runTask(Cosmetics.get(), () -> player.setResourcePack(info.getUrl(), info.getSha1(), false, PACK_COPY));
        });
    }
}
