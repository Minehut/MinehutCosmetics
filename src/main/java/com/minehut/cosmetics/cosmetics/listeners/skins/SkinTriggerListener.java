package com.minehut.cosmetics.cosmetics.listeners.skins;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.CosmeticsManager;
import com.minehut.cosmetics.cosmetics.ui.impl.category.SkinMenu;
import com.minehut.cosmetics.util.SkinUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class SkinTriggerListener implements Listener {

    private static final Component NOTIFICATION = Component.text().append(Component.keybind("key.sneak")).append(Component.space()).append(Component.text("+")).append(Component.space()).append(Component.keybind("key.use")).append(Component.space()).append(Component.text("to open skin menu.")).color(NamedTextColor.GOLD).build();

    private final Cosmetics cosmetics;
    private final CosmeticsManager manager;


    public SkinTriggerListener(Cosmetics cosmetics, CosmeticsManager manager) {
        this.cosmetics = cosmetics;
        this.manager = manager;

        startSkinNotification();
    }

    @EventHandler
    public void onClickTable(PlayerInteractEvent event) {
        if (Action.RIGHT_CLICK_BLOCK != event.getAction()) return;

        final Player player = event.getPlayer();
        final Block block = event.getClickedBlock();
        if (EquipmentSlot.HAND != event.getHand() || block == null) return;
        if (!(Material.CRAFTING_TABLE == block.getType() && player.isSneaking())) return;
        event.setCancelled(true);

        SkinMenu.open(player, player.getInventory().getItemInMainHand());
    }

    private void startSkinNotification() {
        Bukkit.getScheduler().runTaskTimer(cosmetics, () -> {
            Bukkit.getOnlinePlayers().forEach(player -> {
                if (Material.AIR == player.getInventory().getItemInMainHand().getType()) return;
                Material type = SkinUtil.getBaseType(player.getInventory().getItemInMainHand());
                if (!manager.getBindings().hasBinding(type)) return;

                // target block
                Block block = player.getTargetBlock(5);
                if (block == null || block.getType() != Material.CRAFTING_TABLE) return;
                player.sendActionBar(NOTIFICATION);
            });

        }, 0, 10);
    }
}
