package com.minehut.cosmetics.cosmetics.bindings;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class CosmeticBindings {

    private final HashMap<Material, MaterialBinding> bindings = new HashMap<>();

    public void registerBinding(List<MaterialBinding> bindings) {
        bindings.forEach(binding -> binding.getMaterials()
                .forEach(material -> this.bindings.put(material, binding))
        );
    }

    public Optional<MaterialBinding> getBinding(Material material) {
        return Optional.ofNullable(this.bindings.get(material));
    }

    public boolean hasBinding(Material material) {
        return getBinding(material).isPresent();
    }
}
