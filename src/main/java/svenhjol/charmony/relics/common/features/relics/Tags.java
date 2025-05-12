package svenhjol.charmony.relics.common.features.relics;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;
import svenhjol.charmony.relics.RelicsMod;

public final class Tags {
    public static final TagKey<Enchantment> ON_ENCHANTED_TOMES = TagKey.create(Registries.ENCHANTMENT,
        RelicsMod.id("on_enchanted_tomes"));
}
