package com.minehut.cosmetics.commands;

import com.minehut.cosmetics.cosmetics.CosmeticsManager;
import com.minehut.cosmetics.cosmetics.ui.CosmeticMenu;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MenuCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("You must be a player to use this command."));
            return true;
        }

        new CosmeticMenu(player).openTo(player);
        return true;
    }
}
