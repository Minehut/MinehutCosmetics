package com.minehut.cosmetics.commands;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.CosmeticPermission;
import com.minehut.cosmetics.model.profile.CosmeticProfileResponse;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class Debug implements CommandExecutor {

    private final Cosmetics plugin;

    public Debug(Cosmetics plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("You must be a player to use this command."));
            return true;
        }

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            if (!CosmeticPermission.isStaff().apply(player).join()) return;

            sender.sendMessage(Component.text("Retrieving Profile...").color(NamedTextColor.YELLOW));
            final Optional<CosmeticProfileResponse> profile = plugin.cosmeticManager().getProfile(player.getUniqueId()).join();

            if (profile.isEmpty()) {
                player.sendMessage(Component.text("No profile found."));
                return;
            }

            player.sendMessage(Component.text()
                    .append(Component.text("Found profile!").color(NamedTextColor.GREEN))
                    .append(Component.newline())
                    .append(Component.text(profile.get().toString()).color(NamedTextColor.YELLOW))
            );

            final ItemStack item = player.getInventory().getItemInMainHand();
            if (Material.AIR == item.getType()) return;

            Component keylist = Component.empty();

            for (final NamespacedKey key : item.getItemMeta().getPersistentDataContainer().getKeys()) {
                keylist = keylist.append(Component.newline().append(Component.text(key.toString())));
            }

            player.sendMessage(keylist);
        });

        return true;
    }
}
