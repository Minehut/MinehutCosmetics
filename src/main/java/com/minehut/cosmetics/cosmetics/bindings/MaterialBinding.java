package com.minehut.cosmetics.cosmetics.bindings;

import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.function.Supplier;

/**
 * Class for binding a list of materials to a list
 * of cosmetic types
 */
public class MaterialBinding {
    private final HashSet<Material> materials = new HashSet<>();
    private final HashSet<CosmeticSupplier> cosmetics = new HashSet<>();

    public MaterialBinding registerMaterials(List<Material> materials) {
        this.materials.addAll(materials);
        return this;
    }

    public final MaterialBinding registerCosmetics(List<CosmeticSupplier> cosmetics) {
        this.cosmetics.addAll(cosmetics);
        return this;
    }

    public HashSet<CosmeticSupplier> getCosmetics() {
        return cosmetics;
    }

    public HashSet<Material> getMaterials() {
        return materials;
    }

    public boolean hasMaterial(Material material) {
        return materials.contains(material);
    }
}
