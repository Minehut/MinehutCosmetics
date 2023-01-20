package com.minehut.cosmetics.commands.debug;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.commands.Command;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.model.profile.CosmeticProfileResponse;
import com.minehut.cosmetics.util.messaging.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class Debug extends Command {

    private final Cosmetics cosmetics;

    public Debug(Cosmetics cosmetics) {
        super("debugcosmetics");
        this.cosmetics = cosmetics;
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String command, @NotNull List<String> args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Message.error("You must be a player to use this command."));
            return;
        }

    }
}
