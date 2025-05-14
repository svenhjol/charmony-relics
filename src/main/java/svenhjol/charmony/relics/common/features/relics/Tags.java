package svenhjol.charmony.relics.common.features.relics;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;
import svenhjol.charmony.relics.RelicsMod;

public final class Tags {
    public static final TagKey<Enchantment> ON_BOW = TagKey.create(Registries.ENCHANTMENT,
        RelicsMod.id("on_bow"));

    public static final TagKey<Enchantment> ON_CROSSBOW = TagKey.create(Registries.ENCHANTMENT,
        RelicsMod.id("on_crossbow"));

    public static final TagKey<Enchantment> ON_DIAMOND_AXE = TagKey.create(Registries.ENCHANTMENT,
        RelicsMod.id("on_diamond_axe"));

    public static final TagKey<Enchantment> ON_DIAMOND_BOOTS = TagKey.create(Registries.ENCHANTMENT,
        RelicsMod.id("on_diamond_boots"));

    public static final TagKey<Enchantment> ON_DIAMOND_CHESTPLATE = TagKey.create(Registries.ENCHANTMENT,
        RelicsMod.id("on_diamond_chestplate"));

    public static final TagKey<Enchantment> ON_DIAMOND_HELMET = TagKey.create(Registries.ENCHANTMENT,
        RelicsMod.id("on_diamond_helmet"));

    public static final TagKey<Enchantment> ON_DIAMOND_LEGGINGS = TagKey.create(Registries.ENCHANTMENT,
        RelicsMod.id("on_diamond_leggings"));

    public static final TagKey<Enchantment> ON_DIAMOND_PICKAXE = TagKey.create(Registries.ENCHANTMENT,
        RelicsMod.id("on_diamond_pickaxe"));

    public static final TagKey<Enchantment> ON_DIAMOND_SHOVEL = TagKey.create(Registries.ENCHANTMENT,
        RelicsMod.id("on_diamond_shovel"));

    public static final TagKey<Enchantment> ON_DIAMOND_SWORD = TagKey.create(Registries.ENCHANTMENT,
        RelicsMod.id("on_diamond_sword"));

    public static final TagKey<Enchantment> ON_ENCHANTED_TOME = TagKey.create(Registries.ENCHANTMENT,
        RelicsMod.id("on_enchanted_tome"));

    public static final TagKey<Enchantment> ON_FISHING_ROD = TagKey.create(Registries.ENCHANTMENT,
        RelicsMod.id("on_fishing_rod"));

    public static final TagKey<Enchantment> ON_SHIELD = TagKey.create(Registries.ENCHANTMENT,
        RelicsMod.id("on_shield"));

    public static final TagKey<Enchantment> ON_TRIDENT = TagKey.create(Registries.ENCHANTMENT,
        RelicsMod.id("on_trident"));
}
