package svenhjol.charmony.relics.common.features.derelicts;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import svenhjol.charmony.api.derelicts.DerelictDefinition;
import svenhjol.charmony.api.derelicts.DerelictDefinitionProvider;
import svenhjol.charmony.core.Api;
import svenhjol.charmony.core.base.Setup;
import svenhjol.charmony.core.common.CommonRegistry;
import svenhjol.charmony.relics.common.features.derelicts.loot_functions.DerelictMapFunction;
import svenhjol.charmony.relics.common.features.derelicts.structures.Amphitheater;
import svenhjol.charmony.relics.common.features.derelicts.structures.PillarRoom;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Registers extends Setup<Derelicts> {
    public final Supplier<StructureType<DerelictStructure>> structureType;
    public Codec<DerelictDefinition> structureCodec;
    public final Map<String, Supplier<StructurePieceType>> pieceTypes = new HashMap<>();
    public final Map<String, DerelictDefinition> definitions = new HashMap<>();
    public final Supplier<LootItemFunctionType<? extends LootItemConditionalFunction>> mapLootFunction;

    public Registers(Derelicts feature) {
        super(feature);
        var registry = CommonRegistry.forFeature(feature);

        structureType = registry.structure(Constants.STRUCTURE_ID, () -> DerelictStructure.CODEC);

        pieceTypes.put(Constants.PILLAR_ROOM,
            registry.structurePiece(Constants.PILLAR_ROOM, () -> PillarRoom::new));
        pieceTypes.put(Constants.AMPHITHEATER,
            registry.structurePiece(Constants.AMPHITHEATER, () -> Amphitheater::new));

        mapLootFunction = registry.lootFunctionType(Constants.MAP_LOOT,
            () -> new LootItemFunctionType<>(DerelictMapFunction.CODEC));

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
            LootTableEvents.MODIFY.register(feature().handlers::handleLootTableModify);
        };
    }
}
