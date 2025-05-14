package svenhjol.charmony.relics.common.features.derelicts;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import svenhjol.charmony.relics.RelicsMod;

public final class Tags {
    public static final ResourceKey<LootTable> BOOKS_CHEST = ResourceKey.create(Registries.LOOT_TABLE,
        RelicsMod.id("chest/books"));

    public static final ResourceKey<LootTable> DERELICT_ARCHAEOLOGY = ResourceKey.create(Registries.LOOT_TABLE,
        RelicsMod.id("archaeology/derelict"));

    public static final ResourceKey<LootTable> DERELICT_POTS = ResourceKey.create(Registries.LOOT_TABLE,
        RelicsMod.id("pots/derelict"));

    // This loot table intentionally has no pools. A dynamic pool is added at runtime.
    public static final ResourceKey<LootTable> RELICS = ResourceKey.create(Registries.LOOT_TABLE,
        RelicsMod.id("gameplay/relic"));
}
