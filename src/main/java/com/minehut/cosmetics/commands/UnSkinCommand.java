package com.minehut.cosmetics.commands;

import com.minehut.cosmetics.cosmetics.properties.Skinnable;
import com.minehut.cosmetics.util.SkinUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class UnSkinCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("You must be a player to use this command."));
            return true;
        }

        // grab the cosmetic for this item and unskin it if possible
        final ItemStack item = player.getInventory().getItemInMainHand();
        SkinUtil.getCosmetic(item).ifPresentOrElse(cosmetic -> {
            if (!(cosmetic instanceof Skinnable skinnable)) return;
            skinnable.removeSkin(item);
            player.sendMessage(Component.text("Removed item skin").color(NamedTextColor.GREEN));
        }, () -> {
            // tell them if we were unable to skin this item
            player.sendMessage(Component.text("Unable to remove a skin from this item.").color(NamedTextColor.RED));
        });

        return true;
    }
}
