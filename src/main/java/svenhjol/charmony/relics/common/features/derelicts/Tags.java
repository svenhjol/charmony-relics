package svenhjol.charmony.relics.common.features.derelicts;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.storage.loot.LootTable;
import svenhjol.charmony.core.Charmony;

public final class Tags {
    public static final ResourceKey<LootTable> DERELICT_BOOKS_CHEST = ResourceKey.create(Registries.LOOT_TABLE,
        Charmony.id("chests/derelict_books"));

    public static final ResourceKey<LootTable> DERELICT_ARCHAEOLOGY = ResourceKey.create(Registries.LOOT_TABLE,
        Charmony.id("archaeology/derelict"));

    public static final TagKey<Item> DERELICT_RUNESTONE = TagKey.create(Registries.ITEM,
        Charmony.id("runestone/derelict"));

    public static final ResourceKey<LootTable> DERELICT_POTS = ResourceKey.create(Registries.LOOT_TABLE,
        Charmony.id("pots/derelict"));

    public static final TagKey<Structure> ON_DERELICT_MAPS = TagKey.create(Registries.STRUCTURE,
        Charmony.id("on_derelict_maps"));

    public static final TagKey<Structure> RUNESTONE_LOCATED = TagKey.create(Registries.STRUCTURE,
        Charmony.id("runestone_located"));

    public static final ResourceKey<LootTable> RELICS = svenhjol.charmony.relics.common.features.relics.Tags.RELICS;
}
