package com.minehut.cosmetics.cosmetics.listeners.visibility;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.events.CosmeticEntitySpawnEvent;
import com.minehut.cosmetics.cosmetics.events.MinehutPackEvent;
import com.minehut.cosmetics.util.SkinUtil;
import com.minehut.cosmetics.util.Version;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@SuppressWarnings("UnstableApiUsage")
public class VisibilityHandler implements Listener {

    private final Cosmetics cosmetics;

    public VisibilityHandler(Cosmetics cosmetics) {
        this.cosmetics = cosmetics;
    }

    // listen for pack accepted status
    @EventHandler
    public void onPackAccepted(MinehutPackEvent event) {
        setCosmeticVisibility(event.player(), event.accepted());
    }

    @EventHandler
    public void onSpawn(CosmeticEntitySpawnEvent event) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            // ignore players that have accepted
            if (cosmetics.resourcePackManager().hasAccepted(player.getUniqueId())) {
                return;
            }
            setEntityVisibility(player, event.entity(), false);
        });
    }

    /**
     * Set whether a player can see cosmetics or not and
     * show/hide them accordingly
     *
     * @param player  to set visibility for
     * @param visible whether to make them visible or not
     */
    public void setCosmeticVisibility(Player player, boolean visible) {
        if (!Version.V_1_19.isSupported()) return;
        cosmetics.entityHandler().entities().forEach(entity -> setEntityVisibility(player, entity, visible));
    }

    /**
     * Set whether the player should be able to see the given entity
     *
     * @param player  to change visibility for
     * @param entity  target entity to change for
     * @param visible whether the entity should be visible or not
     */
    public void setEntityVisibility(Player player, Entity entity, boolean visible) {
        if (!Version.V_1_19.isSupported()) return;
        // if their state already matches whether they can see, return
        if (player.canSee(entity) == visible) {
            return;
        }
        if (visible) {
            player.showEntity(cosmetics, entity);
        } else {
            player.hideEntity(cosmetics, entity);
        }
    }
}
