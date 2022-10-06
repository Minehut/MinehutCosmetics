package com.minehut.cosmetics.events.skins;

import com.minehut.cosmetics.modules.KeyManager;
import com.minehut.cosmetics.util.ItemUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerItemMendEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class SkinDurabilityListener implements Listener {

    private KeyManager keys;

    public SkinDurabilityListener(KeyManager keys) {
        this.keys = keys;
    }

    /*
     * Handle scaling durability for cosmetic items
     */
    @EventHandler
    public void onDurabilityChange(PlayerItemDamageEvent event) {
        ItemStack item = event.getItem();
        if (!item.hasItemMeta()) return;
        if (!(item.getItemMeta() instanceof Damageable damageable)) return;

        // grab durability nbt from the item
        ItemUtil.editMeta(item, (meta, data) -> {
            Integer storedDurability = data.get(keys.DURABILITY, PersistentDataType.INTEGER);
            Integer storedMax = data.get(keys.MAX_DURABILITY, PersistentDataType.INTEGER);
            if (storedDurability == null || storedMax == null) return;

            storedDurability -= event.getDamage();

            float durabilityPercentage = storedDurability / (float) storedMax;
            int maxDurability = item.getType().getMaxDurability();
            int target = maxDurability - (int) (maxDurability * durabilityPercentage);

            event.setDamage(target - damageable.getDamage());

            data.set(keys.DURABILITY, PersistentDataType.INTEGER, storedDurability);
        });
    }

    @EventHandler
    public void onMend(PlayerItemMendEvent event) {
        ItemStack item = event.getItem();
        if (!item.hasItemMeta()) return;

        // grab durability nbt from the item
        item.editMeta(meta -> {
            PersistentDataContainer data = meta.getPersistentDataContainer();
            Integer storedDurability = data.get(keys.DURABILITY, PersistentDataType.INTEGER);
            Integer storedMax = data.get(keys.MAX_DURABILITY, PersistentDataType.INTEGER);
            if (storedDurability == null || storedMax == null) return;

            storedDurability += event.getRepairAmount();


            // ratio
            int itemMax = item.getType().getMaxDurability();
            float scale = itemMax / (float) storedDurability;

            int scaledRepairAmount = (int) (scale * event.getRepairAmount());
            event.setRepairAmount(scaledRepairAmount);

            data.set(keys.DURABILITY, PersistentDataType.INTEGER, storedDurability);
        });
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        event.setCancelled(true);
    }
}
