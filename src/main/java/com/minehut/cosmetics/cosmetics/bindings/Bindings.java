package com.minehut.cosmetics.cosmetics.bindings;

import com.minehut.cosmetics.cosmetics.groups.hat.Hat;
import com.minehut.cosmetics.cosmetics.groups.item.Item;
import org.bukkit.Material;

import java.util.List;

public class Bindings {
    // bindings for sword items
    public static MaterialBinding SWORD = new MaterialBinding()
            .registerMaterials(List.of(
                    Material.WOODEN_SWORD,
                    Material.STONE_SWORD,
                    Material.IRON_SWORD,
                    Material.GOLDEN_SWORD,
                    Material.DIAMOND_SWORD,
                    Material.NETHERITE_SWORD
            ))
            .registerCosmetics(List.of(
                    Item.EXPLORER_SWORD,
                    Item.FALL_22_SWORD,
                    Item.KATANA
            ));

    // bindings for pickaxe items
    public static MaterialBinding PICKAXE = new MaterialBinding()
            .registerMaterials(List.of(
                    Material.WOODEN_PICKAXE,
                    Material.STONE_PICKAXE,
                    Material.IRON_PICKAXE,
                    Material.GOLDEN_PICKAXE,
                    Material.DIAMOND_PICKAXE,
                    Material.NETHERITE_PICKAXE
            ))
            .registerCosmetics(List.of(
                    Item.EXPLORER_PICKAXE,
                    Item.FALL_22_PICKAXE
            ));

    // bindings for shovel items
    public static MaterialBinding SHOVEL = new MaterialBinding()
            .registerMaterials(List.of(
                    Material.WOODEN_SHOVEL,
                    Material.STONE_SHOVEL,
                    Material.IRON_SHOVEL,
                    Material.GOLDEN_SHOVEL,
                    Material.DIAMOND_SHOVEL,
                    Material.NETHERITE_SHOVEL
            ))
            .registerCosmetics(List.of(
                    Item.FALL_22_SHOVEL,
                    Item.GRAVE_SHOVEL
            ));

    // bindings for axe items
    public static MaterialBinding AXE = new MaterialBinding()
            .registerMaterials(List.of(
                    Material.WOODEN_AXE,
                    Material.STONE_AXE,
                    Material.IRON_AXE,
                    Material.GOLDEN_AXE,
                    Material.DIAMOND_AXE,
                    Material.NETHERITE_AXE
            ))
            .registerCosmetics(List.of(
                    Item.FALL_22_AXE
            ));

    // bindings for spyglass items
    public static MaterialBinding SPYGLASS = new MaterialBinding()
            .registerMaterials(List.of(
                    Material.SPYGLASS
            ))
            .registerCosmetics(List.of(
                    Item.EXPLORER_SPYGLASS
            ));

    public static MaterialBinding HAT = new MaterialBinding()
            .registerMaterials(List.of(
                    Material.LEATHER_HELMET,
                    Material.GOLDEN_HELMET,
                    Material.CHAINMAIL_HELMET,
                    Material.IRON_HELMET,
                    Material.DIAMOND_HELMET,
                    Material.NETHERITE_HELMET,
                    Material.TURTLE_HELMET
            ))
            .registerCosmetics(List.of(
                    // Explorer
                    Hat.EXPLORER,
                    // Spooktacular 2022
                    Hat.WITCH,
                    Hat.CAT_EARS,
                    Hat.DEVIL_HORNS,
                    Hat.FOX_EARS,
                    Hat.FOX_EARS
            ));

    public static MaterialBinding SHIELD = new MaterialBinding()
            .registerMaterials(List.of(
                    Material.SHIELD
            ))
            .registerCosmetics(List.of(
                    Item.FALL_22_SHIELD
            ));


    public static List<MaterialBinding> ALL = List.of(
            SWORD,
            PICKAXE,
            SHOVEL,
            AXE,
            SPYGLASS,
            HAT,
            SHIELD
    );
}
