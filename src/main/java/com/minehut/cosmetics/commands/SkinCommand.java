package com.minehut.cosmetics.commands;

import com.minehut.cosmetics.cosmetics.CosmeticsManager;
import com.minehut.cosmetics.cosmetics.ui.SkinMenu;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class SkinCommand implements CommandExecutor {

    private final CosmeticsManager manager;
    private final Plugin plugin;

    public SkinCommand(Plugin plugin, CosmeticsManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("You must be a player to use this command."));
            return true;
        }


        SkinMenu.open(plugin, manager, player, player.getInventory().getItemInMainHand());

        return true;
    }
}
