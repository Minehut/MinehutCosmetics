package com.minehut.cosmetics.cosmetics.listeners.skins;

import com.minehut.cosmetics.util.data.Key;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerItemMendEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class SkinDurabilityListener implements Listener {

    /*
     * Handle scaling durability for cosmetic items
     */
    @EventHandler
    public void onDurabilityChange(PlayerItemDamageEvent event) {
        ItemStack item = event.getItem();
        if (!item.hasItemMeta()) return;
        if (!(item.getItemMeta() instanceof Damageable damageable)) return;

        item.editMeta(meta -> Key.DURABILITY.read(meta).ifPresent(storedCurrent -> {
            Key.MAX_DURABILITY.read(meta).ifPresent(storedMax -> {
                int durability = storedCurrent;
                durability -= event.getDamage();

                float durabilityPercentage = durability / (float) storedMax;
                int maxDurability = item.getType().getMaxDurability();
                int target = maxDurability - (int) (maxDurability * durabilityPercentage);

                event.setDamage(target - damageable.getDamage());

                Key.DURABILITY.write(meta, durability);
            });
        }));
    }

    @EventHandler
    public void onMend(PlayerItemMendEvent event) {
        ItemStack item = event.getItem();
        if (!item.hasItemMeta()) return;

        // grab durability nbt from the item
        item.editMeta(meta -> Key.DURABILITY.read(meta).ifPresent(currentDurability -> Key.MAX_DURABILITY.read(meta).ifPresent(maxDurability -> {
            int durability = currentDurability;
            durability += event.getRepairAmount();

            // ratio
            int itemMax = item.getType().getMaxDurability();
            float scale = itemMax / (float) durability;

            int scaledRepairAmount = (int) (scale * event.getRepairAmount());
            event.setRepairAmount(scaledRepairAmount);

            Key.DURABILITY.write(meta, durability);
        })));
    }
}
