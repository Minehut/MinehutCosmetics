package com.minehut.cosmetics.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class Command extends BukkitCommand {

    protected Command(@NotNull String name) {
        super(name);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        execute(sender, commandLabel, List.of(args));
        return true;
    }

    public abstract void execute(@NotNull CommandSender sender, @NotNull String command, @NotNull List<String> args);

    public void register(Plugin plugin) {
        plugin.getServer().getCommandMap().register(plugin.getName(), this);
    }
}