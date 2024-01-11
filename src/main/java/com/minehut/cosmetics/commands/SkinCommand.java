package com.minehut.cosmetics.commands;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.ui.impl.category.SkinMenu;
import com.minehut.cosmetics.util.messaging.Message;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SkinCommand extends Command {

    public SkinCommand(Cosmetics cosmetics) {
        super("skin");
        setDescription("Opens the skin menu for the held item");
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String command, @NotNull List<String> args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Message.error(Component.text("You must be a player to use this command.")));
            return;
        }

        SkinMenu.open(player, player.getInventory().getItemInMainHand());
    }
}