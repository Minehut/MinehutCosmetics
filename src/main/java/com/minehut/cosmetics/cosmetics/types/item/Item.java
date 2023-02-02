package com.minehut.cosmetics.cosmetics.types.item;

import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.collections.autumn2022.Fall2022Axe;
import com.minehut.cosmetics.cosmetics.collections.autumn2022.Fall2022Shield;
import com.minehut.cosmetics.cosmetics.collections.autumn2022.Fall22Arrow;
import com.minehut.cosmetics.cosmetics.collections.autumn2022.Fall22Bow;
import com.minehut.cosmetics.cosmetics.collections.autumn2022.Fall22LeafSword;
import com.minehut.cosmetics.cosmetics.collections.autumn2022.Fall22Pickaxe;
import com.minehut.cosmetics.cosmetics.collections.autumn2022.Fall22Shovel;
import com.minehut.cosmetics.cosmetics.collections.autumn2022.Fall22Sword;
import com.minehut.cosmetics.cosmetics.collections.beta.ExplorerPickaxe;
import com.minehut.cosmetics.cosmetics.collections.beta.ExplorerSpyglass;
import com.minehut.cosmetics.cosmetics.collections.beta.ExplorerSword;
import com.minehut.cosmetics.cosmetics.collections.betacrate.BanHammer;
import com.minehut.cosmetics.cosmetics.collections.betacrate.DragonCleaver;
import com.minehut.cosmetics.cosmetics.collections.betacrate.FancyFishingRod;
import com.minehut.cosmetics.cosmetics.collections.betacrate.FishSword;
import com.minehut.cosmetics.cosmetics.collections.betacrate.MoltenPickaxe;
import com.minehut.cosmetics.cosmetics.collections.betacrate.ShortSword;
import com.minehut.cosmetics.cosmetics.collections.crusader.CrusaderSword;
import com.minehut.cosmetics.cosmetics.collections.dev.Katana;
import com.minehut.cosmetics.cosmetics.collections.general.ArcadeAxe;
import com.minehut.cosmetics.cosmetics.collections.general.IcePickaxe;
import com.minehut.cosmetics.cosmetics.collections.general.IceSword;
import com.minehut.cosmetics.cosmetics.collections.halloween2022.GraveShovel;
import com.minehut.cosmetics.cosmetics.collections.halloween2022.Halloween22Scythe;
import com.minehut.cosmetics.cosmetics.collections.jan2023.EightBitSword;
import com.minehut.cosmetics.cosmetics.collections.jan2023.Kunai;
import com.minehut.cosmetics.cosmetics.collections.jan2023.Ninjato;
import com.minehut.cosmetics.cosmetics.collections.maid.MaidDuster;
import com.minehut.cosmetics.cosmetics.collections.winter2022.IceAxe;
import com.minehut.cosmetics.cosmetics.collections.winter2022.IceShield;
import com.minehut.cosmetics.cosmetics.collections.winter2022.IceShovel;

import java.util.function.Supplier;

public enum Item implements CosmeticSupplier<ItemCosmetic> {
    // Explorer Set
    EXPLORER_SPYGLASS(ExplorerSpyglass::new),
    EXPLORER_PICKAXE(ExplorerPickaxe::new),
    EXPLORER_SWORD(ExplorerSword::new),
    // Fall Set
    FALL_22_PICKAXE(Fall22Pickaxe::new),
    FALL_22_SHOVEL(Fall22Shovel::new),
    FALL_22_SWORD(Fall22Sword::new),
    FALL_22_LEAF_SWORD(Fall22LeafSword::new),
    FALL_22_AXE(Fall2022Axe::new),
    FALL_22_ARROW(Fall22Arrow::new),
    FALL_22_BOW(Fall22Bow::new),
    // shields
    FALL_22_SHIELD(Fall2022Shield::new),
    // Spooktacular
    GRAVE_SHOVEL(GraveShovel::new),
    HALLO_22_SCYTHE(Halloween22Scythe::new),
    // special
    KATANA(Katana::new),
    // maid
    MAID_DUSTER(MaidDuster::new),
    // crusader Collection
    CRUSADER_SWORD(CrusaderSword::new),
    // legendary crates
    FISH_SWORD(FishSword::new),
    FANCY_SHORT_SWORD(ShortSword::new),
    DRAGON_SWORD(DragonCleaver::new),
    FANCY_FISHING_ROD(FancyFishingRod::new),
    MOLTEN_PICKAXE(MoltenPickaxe::new),
    BAN_HAMMER(BanHammer::new),
    // ice collection
    ICE_SWORD(IceSword::new),
    ICE_PICKAXE(IcePickaxe::new),
    ICE_AXE(IceAxe::new),
    ICE_SHOVEL(IceShovel::new),
    ICE_SHIELD(IceShield::new),
    // arcade collection
    ARCADE_AXE(ArcadeAxe::new),
    EIGHT_BIT_SWORD(EightBitSword::new),
    KUNAI(Kunai::new),
    NINJATO(Ninjato::new);

    private final Supplier<ItemCosmetic> supplier;

    Item(final Supplier<ItemCosmetic> supplier) {
        this.supplier = supplier;
    }

    @Override
    public ItemCosmetic get() {
        return supplier.get();
    }
}
