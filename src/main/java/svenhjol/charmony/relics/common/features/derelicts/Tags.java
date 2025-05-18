package svenhjol.charmony.relics.common.features.derelicts;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.storage.loot.LootTable;
import svenhjol.charmony.relics.RelicsMod;

public final class Tags {
    public static final ResourceKey<LootTable> BOOKS_CHEST = ResourceKey.create(Registries.LOOT_TABLE,
        RelicsMod.id("chest/books"));

    public static final ResourceKey<LootTable> DERELICT_ARCHAEOLOGY = ResourceKey.create(Registries.LOOT_TABLE,
        RelicsMod.id("archaeology/derelict"));

    public static final TagKey<Item> DERELICT_RUNESTONE = TagKey.create(Registries.ITEM,
        RelicsMod.id("runetone/derelict"));

    public static final ResourceKey<LootTable> DERELICT_POTS = ResourceKey.create(Registries.LOOT_TABLE,
        RelicsMod.id("pots/derelict"));

    // This loot table intentionally has no pools. A dynamic pool is added at runtime.
    public static final ResourceKey<LootTable> RELICS = ResourceKey.create(Registries.LOOT_TABLE,
        RelicsMod.id("gameplay/relic"));

    public static final TagKey<Structure> ON_DERELICT_MAPS = TagKey.create(Registries.STRUCTURE,
        RelicsMod.id("on_derelict_maps"));

    public static final TagKey<Structure> RUNESTONE_LOCATED = TagKey.create(Registries.STRUCTURE,
        RelicsMod.id("runestone_located"));
}
