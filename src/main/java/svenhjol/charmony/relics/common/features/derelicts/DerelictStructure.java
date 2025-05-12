package svenhjol.charmony.relics.common.features.derelicts;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import svenhjol.charmony.api.derelicts.DerelictDefinition;
import svenhjol.charmony.relics.common.features.derelicts.providers.DerelictDefinitionProviders;
import svenhjol.charmony.relics.common.features.derelicts.structures.Amphitheater;

import java.util.Optional;

public class DerelictStructure extends Structure {
    public static final MapCodec<DerelictStructure> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            DerelictStructure.settingsCodec(instance),
            Derelicts.feature().registers.structureCodec.fieldOf("derelict_definition")
                .forGetter(structure -> structure.definition))
        .apply(instance, DerelictStructure::new));

    private final DerelictDefinition definition;

    protected DerelictStructure(StructureSettings structureSettings, DerelictDefinition definition) {
        super(structureSettings);
        this.definition = definition;
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        var chunkPos = context.chunkPos();
        var blockPos = new BlockPos(chunkPos.getMiddleBlockX(), -55, chunkPos.getMinBlockZ());
        var builder = new StructurePiecesBuilder();

        if (tryGeneratePieces(builder, context)) {
            var stub = new Structure.GenerationStub(blockPos, Either.right(builder));
            return Optional.of(stub);
        }

        return Optional.empty();
    }

    protected boolean tryGeneratePieces(StructurePiecesBuilder builder, GenerationContext context) {
        var random = context.random();
        var chunkPos = context.chunkPos();
        var x = chunkPos.getBlockX(2);
        var y = -62;
        var z = chunkPos.getBlockZ(2);

        var width = 48 + (random.nextInt(8) * 2);
        var height = -53 + (random.nextInt(6) * 2);

        var box = new BoundingBox(x, y, z, x + width, height, z + width);

        switch (definition.name()) {
            case DerelictDefinitionProviders.AMPHITHEATER: {
                builder.addPiece(new Amphitheater(definition, 0, random, box));
                return true;
            }
            default: {
                return false;
            }
        }
    }

    @Override
    public StructureType<?> type() {
        return Derelicts.feature().registers.structureType.get();
    }
}
