package com.minehut.cosmetics.cosmetics.bindings;

import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import org.bukkit.Material;

import java.util.HashSet;
import java.util.List;

/**
 * Class for binding a list of materials to a list
 * of cosmetic types
 */
public class MaterialBinding {
    private final HashSet<Material> materials = new HashSet<>();
    private final HashSet<CosmeticSupplier<? extends Cosmetic>> cosmetics = new HashSet<>();

    public MaterialBinding registerMaterials(List<Material> materials) {
        this.materials.addAll(materials);
        return this;
    }

    public final MaterialBinding registerCosmetics(List<CosmeticSupplier<? extends Cosmetic>> cosmetics) {
        this.cosmetics.addAll(cosmetics);
        return this;
    }

    public HashSet<CosmeticSupplier<? extends Cosmetic>> getCosmetics() {
        return cosmetics;
    }

    public HashSet<Material> getMaterials() {
        return materials;
    }

    public boolean hasMaterial(Material material) {
        return materials.contains(material);
    }
}
