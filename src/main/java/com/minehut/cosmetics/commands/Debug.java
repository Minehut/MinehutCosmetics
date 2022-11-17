package com.minehut.cosmetics.commands;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.model.profile.CosmeticProfileResponse;
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
            sender.sendMessage(Component.text("You must be a player to use this command."));
            return;
        }


        Bukkit.getScheduler().runTaskAsynchronously(cosmetics, () -> {
            if (!Permission.staff().hasAccess(player).join()) return;

            sender.sendMessage(Component.text("Retrieving Profile...").color(NamedTextColor.YELLOW));
            final Optional<CosmeticProfileResponse> profile = cosmetics.cosmeticManager().getProfile(player.getUniqueId()).join();

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
    }
}
