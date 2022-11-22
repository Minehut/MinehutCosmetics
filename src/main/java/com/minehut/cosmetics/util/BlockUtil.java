package com.minehut.cosmetics.util;

import org.bukkit.Material;
import org.bukkit.Tag;

import java.util.HashSet;
import java.util.Set;

public class BlockUtil {

    public static Set<Material> INTERACTABLE = new HashSet<>();

    static {
        // fence gates
        INTERACTABLE.addAll(Tag.FENCE_GATES.getValues());

        // camp fires
        INTERACTABLE.addAll(Tag.CAMPFIRES.getValues());

        // doors
        INTERACTABLE.addAll(Tag.DOORS.getValues());

        // Inventory Blocks
        INTERACTABLE.addAll(Set.of(
                Material.CHEST,
                Material.TRAPPED_CHEST,
                Material.SHULKER_BOX,
                Material.ENDER_CHEST,
                Material.BARREL
        ));

        // Smelting
        INTERACTABLE.addAll(Set.of(
                Material.FURNACE,
                Material.BLAST_FURNACE,
                Material.SMOKER
        ));

        // Enchanting
        INTERACTABLE.addAll(Set.of(
                Material.ENCHANTING_TABLE,
                Material.ANVIL,
                Material.STONECUTTER
        ));

        // crafting
        INTERACTABLE.addAll(Set.of(
                Material.CRAFTING_TABLE,
                Material.SMITHING_TABLE
        ));

        // Misc
        INTERACTABLE.add(
                Material.COMPOSTER
        );
    }

    public static boolean canInteract(Material material) {
        return INTERACTABLE.contains(material);
    }
}
