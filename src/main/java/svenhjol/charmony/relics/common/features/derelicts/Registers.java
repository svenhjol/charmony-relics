package svenhjol.charmony.relics.common.features.derelicts;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import svenhjol.charmony.api.derelicts.DerelictDefinition;
import svenhjol.charmony.api.derelicts.DerelictDefinitionProvider;
import svenhjol.charmony.core.Api;
import svenhjol.charmony.core.base.Setup;
import svenhjol.charmony.core.common.CommonRegistry;
import svenhjol.charmony.relics.common.features.derelicts.structures.Amphitheater;
import svenhjol.charmony.relics.common.features.derelicts.structures.PillarRoom;
import svenhjol.charmony.relics.common.features.relics.loot_functions.BookLootFunction;
import svenhjol.charmony.relics.common.features.relics.loot_functions.RelicLootFunction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class Registers extends Setup<Derelicts> {
    public final Supplier<StructureType<DerelictStructure>> structureType;
    public Codec<DerelictDefinition> structureCodec;
    public final Map<String, Supplier<StructurePieceType>> pieceTypes = new HashMap<>();
    public final Map<String, DerelictDefinition> definitions = new HashMap<>();

    public Registers(Derelicts feature) {
        super(feature);
        var registry = CommonRegistry.forFeature(feature);

        structureType = registry.structure(Constants.STRUCTURE_ID, () -> DerelictStructure.CODEC);

        pieceTypes.put(Constants.PILLAR_ROOM,
            registry.structurePiece(Constants.PILLAR_ROOM, () -> PillarRoom::new));
        pieceTypes.put(Constants.AMPHITHEATER,
            registry.structurePiece(Constants.AMPHITHEATER, () -> Amphitheater::new));

        Api.consume(DerelictDefinitionProvider.class, provider -> {
            for (var def : provider.getDerelictDefinitions()) {
                definitions.put(def.name(), def);
            }

            structureCodec = StringRepresentable.fromValues(
                () -> definitions.values().toArray(new DerelictDefinition[0]));
        });
    }

    @Override
    public Runnable boot() {
        return () -> {
            LootTableEvents.MODIFY.register(this::handleLootTableModify);
        };
    }

    private void handleLootTableModify(ResourceKey<LootTable> key, LootTable.Builder builder, LootTableSource source, HolderLookup.Provider provider) {
        if (!source.isBuiltin()) return;
        if (key == Tags.BOOKS_CHEST) {
            var pool = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(Items.BOOK)
                    .setWeight(1)
                    .apply(() -> new BookLootFunction(List.of())));

            builder.pool(pool.build());
        }
        if (key == Tags.RELICS) {
            var pool = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(Items.DIAMOND)
                    .setWeight(1)
                    .apply(() -> new RelicLootFunction(List.of())));

            builder.pool(pool.build());
        }
    }
}
