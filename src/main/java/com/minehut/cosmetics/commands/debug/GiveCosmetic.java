package com.minehut.cosmetics.commands.debug;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.commands.Command;
import com.minehut.cosmetics.config.Mode;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.model.profile.CosmeticData;
import com.minehut.cosmetics.model.profile.CosmeticMeta;
import com.minehut.cosmetics.model.request.UnlockCosmeticRequest;
import com.minehut.cosmetics.util.messaging.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GiveCosmetic extends Command {
    public GiveCosmetic() {
        super("givecosmetic");
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String command, @NotNull List<String> args) {
        if (!(sender instanceof Player player)) return;
        if (Cosmetics.mode() != Mode.LOBBY) return;

        if (args.size() < 4) {
            player.sendMessage(Message.info("/givecosmetic <player> <category> <id> <amount>"));
            return;
        }

        final Player target = Bukkit.getPlayer(args.get(0));
        if (target == null) return;

        Bukkit.getScheduler().runTaskAsynchronously(Cosmetics.get(), () -> {
            final boolean staff = Permission.staff().hasAccess(player).join();
            if (!staff) return;

            final String category = args.get(1);
            final String id = args.get(2);
            final int amount = Integer.parseInt(args.get(3));

            final CosmeticMeta meta = new CosmeticMeta(amount);
            final CosmeticData data = new CosmeticData(category, id, meta);
            final UnlockCosmeticRequest req = new UnlockCosmeticRequest(target.getUniqueId(), data);
            Cosmetics.get().api().unlockCosmetic(req).join();

            player.sendMessage(Message.info("Gave cosmetic."));
        });
    }
}
