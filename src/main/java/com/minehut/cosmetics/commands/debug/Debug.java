package com.minehut.cosmetics.commands.debug;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.commands.Command;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.crates.Crate;
import com.minehut.cosmetics.cosmetics.crates.CrateType;
import com.minehut.cosmetics.util.EnumUtil;
import com.minehut.cosmetics.util.messaging.Message;
import com.minehut.cosmetics.util.structures.Pair;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Debug extends Command {

    public Debug() {
        super("debugcosmetics");
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String command, @NotNull List<String> args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Message.error("You must be a player to use this command."));
            return;
        }

        if (args.size() < 1) {
            player.sendMessage(Message.error("You must supply an argument."));
            return;
        }

        Permission.staff().hasAccess(player).thenAccept(isStaff -> {
            if (!isStaff) {
                player.sendMessage(Message.error("Only staff can run this command."));
                return;
            }

            switch (args.get(0)) {
                // play the crate opening animation
                case "open-crate" -> {
                    if (args.size() < 2) {
                        player.sendMessage(Message.error("You must supply a crate name."));
                        return;
                    }

                    EnumUtil.valueOfSafe(CrateType.class, args.get(1)).ifPresent(type -> {
                        final Crate crate = type.get();
                        final Pair<CosmeticSupplier<? extends Cosmetic>, Integer> result = crate.getTable().roll();

                        Bukkit.getScheduler().runTask(Cosmetics.get(), () -> type.get().playOpenAnimation(player, player.getLocation(), result.left().get(), () -> {}));
                    });
                }
            }
        });
    }
}
