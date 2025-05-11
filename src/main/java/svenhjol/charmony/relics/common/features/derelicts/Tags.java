package svenhjol.charmony.relics.common.features.derelicts;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import svenhjol.charmony.relics.RelicsMod;

public final class Tags {
    public static final ResourceKey<LootTable> LOOT_DIAMONDS = ResourceKey.create(Registries.LOOT_TABLE,
        RelicsMod.id("chest/diamonds"));
}
