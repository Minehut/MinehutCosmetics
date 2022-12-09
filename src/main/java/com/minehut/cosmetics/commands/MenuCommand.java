package com.minehut.cosmetics.commands;

import com.minehut.cosmetics.cosmetics.ui.CosmeticMenu;
import com.minehut.cosmetics.util.messaging.Message;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MenuCommand extends Command {
    public MenuCommand() {
        super("cosmetics");

        setDescription("Opens the cosmetics menu.");
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String command, @NotNull List<String> args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Message.error(Component.text("You must be a player to use this command.")));
            return;
        }

        new CosmeticMenu(player).openTo(player);
    }
}
