package svenhjol.charmony.relics.common.features.derelicts.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import svenhjol.charmony.api.DerelictDefinition;
import svenhjol.charmony.relics.common.features.derelicts.Constants;
import svenhjol.charmony.relics.common.features.derelicts.DerelictPiece;

public class PillarRoom extends DerelictPiece {
    protected PillarRoom(DerelictDefinition definition, int genDepth, RandomSource random, BoundingBox box) {
        super(feature().registers.pieceTypes.get(Constants.PILLAR_ROOM).get(), definition, genDepth, box);
    }

    public PillarRoom(StructurePieceSerializationContext context, CompoundTag tag) {
        super(feature().registers.pieceTypes.get(Constants.PILLAR_ROOM).get(), tag);
    }

    @Override
    public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos blockPos) {
        var block = Blocks.COBBLESTONE.defaultBlockState();

        generateBox(level, box, boundingBox.minX(), boundingBox.minY() + 1, boundingBox.minZ(),
            boundingBox.maxX(), Math.min(boundingBox.minY() + 3, boundingBox.maxY()), boundingBox.maxZ(), block, block, false);

        generateUpperHalfSphere(level, box, boundingBox.minX(), boundingBox.minY() + 4, boundingBox.minZ(),
            boundingBox.maxX(), boundingBox.maxY(), boundingBox.maxZ(), CAVE_AIR, false);
    }
}
