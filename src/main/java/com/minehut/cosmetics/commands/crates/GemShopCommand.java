package com.minehut.cosmetics.commands.crates;

import com.minehut.cosmetics.commands.Command;
import com.minehut.cosmetics.cosmetics.ui.impl.crates.GemShopMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GemShopCommand extends Command {
    public GemShopCommand() {
        super("gemshop");
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String command, @NotNull List<String> args) {
        if (!(sender instanceof Player player)) return;
        GemShopMenu.open(player);
    }
}
