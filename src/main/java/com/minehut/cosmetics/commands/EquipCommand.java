package com.minehut.cosmetics.commands;

import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticsManager;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.properties.CosmeticSlot;
import com.minehut.cosmetics.util.EnumUtil;
import com.minehut.cosmetics.util.messaging.Message;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EquipCommand extends Command {

    private final CosmeticsManager manager;
    private final Plugin plugin;

    public EquipCommand(Plugin plugin, CosmeticsManager cosmeticsManager) {
        super("equipcosmetic");
        setDescription("Equips a given cosmetic");

        this.plugin = plugin;
        this.manager = cosmeticsManager;
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String command, @NotNull List<String> args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Message.error(Component.text("You must be a player to use this command.")));
            return;
        }

        if (args.size() < 3) {
            player.sendMessage(Message.info("Usage: [category] [cosmetic id] [slot]"));
            return;
        }

        CosmeticCategory category = EnumUtil.valueOfSafe(CosmeticCategory.class, args.get(0)).orElse(null);
        if (category == null) {
            player.sendMessage(Message.error("Invalid category"));
            return;
        }

        Cosmetic cosmetic = category.cosmetic(args.get(1)).orElse(null);
        if (cosmetic == null) {
            player.sendMessage(Message.error("Invalid cosmetic id."));
            return;
        }

        CosmeticSlot slot = EnumUtil.valueOfSafe(CosmeticSlot.class, args.get(2)).orElse(null);
        if (slot == null) {
            player.sendMessage(Message.error("Invalid slot specified."));
            return;
        }

        // check for permission
        Permission.any(Permission.staff(), cosmetic.permission()).hasAccess(player).thenAccept(permission -> {
            if (Boolean.FALSE.equals(permission)) {
                player.sendMessage(Message.error("You do not have this cosmetic unlocked."));
                return;
            }

            Bukkit.getScheduler().runTask(plugin, () -> {
                manager.applyCosmetic(player.getUniqueId(), slot, cosmetic);
                manager.updateEquipment(player.getUniqueId());
            });
        });
    }
}
