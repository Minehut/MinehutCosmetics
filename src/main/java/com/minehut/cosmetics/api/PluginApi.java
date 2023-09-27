package com.minehut.cosmetics.api;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.config.Mode;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticsManager;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.cosmetics.properties.CosmeticSlot;
import com.minehut.cosmetics.util.messaging.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PluginApi {

    private final Cosmetics cosmetics;

    public PluginApi(Cosmetics cosmetics) {
        this.cosmetics = cosmetics;
    }

    public void equip(Player player, Cosmetic cosmetic, CosmeticSlot slot, boolean checkPermission, boolean persist) {

        // only works in lobby mode
        if (Cosmetics.mode() != Mode.LOBBY) {
            return;
        }


        CosmeticsManager manager = cosmetics.manager();
        if (checkPermission) {
            // check for permission
            Permission.any(Permission.staff(), cosmetic.permission()).hasAccess(player).thenAccept((permission) -> {
                if (!permission) {
                    player.sendMessage(Message.error("You do not have this cosmetic unlocked."));
                    return;
                }

                Bukkit.getScheduler().runTask(cosmetics, () -> manager.setCosmetic(player.getUniqueId(), slot, cosmetic, true));
            });
            return;
        }

        manager.setCosmetic(player.getUniqueId(), slot, cosmetic, false);
    }
}
