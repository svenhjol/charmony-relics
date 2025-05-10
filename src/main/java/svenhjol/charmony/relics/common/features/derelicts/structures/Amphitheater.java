package svenhjol.charmony.relics.common.features.derelicts.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import svenhjol.charmony.api.DerelictDefinition;
import svenhjol.charmony.relics.common.features.derelicts.Constants;
import svenhjol.charmony.relics.common.features.derelicts.DerelictPiece;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class Amphitheater extends DerelictPiece {
    private final RandomSource random;
    private float airDecay = 0.0f;
    private float sculkDecay = 0.0f;

    private final Map<BlockState, Float> stepBlockMap = new LinkedHashMap<>();
    private final Map<BlockState, Float> pillarBlockMap = new LinkedHashMap<>();
    private final Map<BlockState, Float> sculkBlockMap = new LinkedHashMap<>();

    private final PillarBlockSelector pillarBlocks = new PillarBlockSelector();
    private final StepBlockSelector stepBlocks = new StepBlockSelector();

    public Amphitheater(DerelictDefinition definition, int genDepth, RandomSource random, BoundingBox box) {
        super(feature().registers.pieceTypes.get(Constants.AMPHITHEATER).get(), definition, genDepth, box);
        this.random = random;
    }

    public Amphitheater(StructurePieceSerializationContext context, CompoundTag tag) {
        super(feature().registers.pieceTypes.get(Constants.AMPHITHEATER).get(), tag);
        this.random = RandomSource.create();
    }

    private void setupBlocks() {
        stepBlockMap.clear();
        pillarBlockMap.clear();

        stepBlockMap.put(Blocks.POLISHED_DEEPSLATE.defaultBlockState(), 0.7f);
        stepBlockMap.put(Blocks.DEEPSLATE_BRICKS.defaultBlockState(), 0.7f);
        stepBlockMap.put(Blocks.CRACKED_DEEPSLATE_BRICKS.defaultBlockState(), 0.6f);
        stepBlockMap.put(Blocks.GRAVEL.defaultBlockState(), 0.4f);
        stepBlockMap.put(Blocks.DEEPSLATE_TILES.defaultBlockState(), 0.4f);
        stepBlockMap.put(Blocks.COBBLED_DEEPSLATE.defaultBlockState(), 0.9f);

        pillarBlockMap.put(Blocks.DEEPSLATE_BRICKS.defaultBlockState(), 0.64f);
        pillarBlockMap.put(Blocks.CRACKED_DEEPSLATE_BRICKS.defaultBlockState(), 0.7f);
        pillarBlockMap.put(Blocks.DEEPSLATE_TILES.defaultBlockState(), 0.9f);
        pillarBlockMap.put(Blocks.GRAVEL.defaultBlockState(), 0.1f);
        pillarBlockMap.put(Blocks.COBBLED_DEEPSLATE.defaultBlockState(), 0.96f);

        sculkBlockMap.put(Blocks.SCULK.defaultBlockState(), 0.85f);
        sculkBlockMap.put(Blocks.SCULK_CATALYST.defaultBlockState(), 0.05f);
    }

    @Override
    public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos blockPos) {
        setupBlocks();

        var minX = boundingBox.minX();
        var minY = boundingBox.minY();
        var minZ = boundingBox.minZ();
        var maxX = boundingBox.maxX();
        var maxY = boundingBox.maxY();
        var maxZ = boundingBox.maxZ();

        // Clear everything above the base
        generateUpperHalfSphere(level, box, minX, minY + 1, minZ, maxX, maxY, maxZ, CAVE_AIR, false);

        // Create main corner pillars
        var pillarHeight = maxY - minY - 2;
        airDecay = 0f;
        sculkDecay = 0.05f;
        generateCornerPillars(level, box, 0, pillarHeight, 12);

        // Generate slabs on top of the pillars
        airDecay = 0.008f;
        sculkDecay = 0.12f;
        generateSingleSlab(level, box, minX, pillarHeight, minZ, 12, 2);
        generateSingleSlab(level, box, maxX - 12, pillarHeight, minZ, 12, 2);
        generateSingleSlab(level, box, minX, pillarHeight, maxZ - 12, 12, 2);
        generateSingleSlab(level, box, maxX - 12, pillarHeight + 1, maxZ - 12, 12, 2);

        // Create main pillar walkways
        airDecay = 0f;
        sculkDecay = 0.0f;
        generateMirroredSteps(level, box, 0, 5, 10);

        // Create steps leading down into the pit
        airDecay = 0.0005f;
        sculkDecay = 0.01f;
        generateMirroredSteps(level, box, 8, 4, 4);
        generateMirroredSteps(level, box, 10, 3, 4);
        generateMirroredSteps(level, box, 12, 2, 4);
        generateMirroredSteps(level, box, 14, 1, 4);

        // Create supporting pillars
        airDecay = 0.01f;
        sculkDecay = 0.0f;
        generateCornerPillars(level, box, 11, 5, 1);
        generateCornerPillars(level, box, 13, 4, 1);
        generateCornerPillars(level, box, 15, 3, 1);
        generateCornerPillars(level, box, 17, 2, 1);
        generateCornerPillars(level, box, 19, 1, 1);

        // Create the base slab.
        var width = (maxX - minX);
        generateSingleSlab(level, box, minX + 17, minY, minZ + 17, width, 0);
        generateDangerSculk(level, box, minX + 17, minY, minZ + 17, minX + 17 + width, minY, minZ + 17 + width);
    }

    protected void generateCornerPillars(WorldGenLevel level, BoundingBox box, int wallOffset, int height, int width) {
        var minX = boundingBox.minX() + wallOffset;
        var minY = boundingBox.minY();
        var minZ = boundingBox.minZ() + wallOffset;
        var maxX = boundingBox.maxX() - wallOffset;
        var maxZ = boundingBox.maxZ() - wallOffset;

        generateBox(level, box, minX, minY + 1, minZ, minX + width, minY + height, minZ + width, false, random, pillarBlocks);
        generateBox(level, box, maxX - width, minY + 1, minZ, maxX, minY + height, minZ + width, false, random, pillarBlocks);
        generateBox(level, box, minX, minY + 1, maxZ - width, minX + width, minY + height, maxZ, false, random, pillarBlocks);
        generateBox(level, box, maxX - width, minY + 1, maxZ - width, maxX, minY + height, maxZ, false, random, pillarBlocks);
    }

    protected void generateSingleSlab(WorldGenLevel level, BoundingBox box, int x, int y, int z, int width, int height) {
        generateBox(level, box, x, y, z, x + width, y + height, z + width, false, random, stepBlocks);
        generateSusBlocks(level, box, x, y, z, x + width, y + height, x + width);
    }

    protected void generateMirroredSteps(WorldGenLevel level, BoundingBox box, int wallOffset, int height, int width) {
        var minX = boundingBox.minX() + wallOffset;
        var minY = boundingBox.minY();
        var minZ = boundingBox.minZ() + wallOffset;
        var maxX = boundingBox.maxX() - wallOffset;
        var maxZ = boundingBox.maxZ() - wallOffset;

        var topLevel = minY + height;

        generateBox(level, box, minX + width, minY, minZ, maxX - width, topLevel, minZ + width, false, random, stepBlocks);
        generateSusBlocks(level, box, minX + width, topLevel, minZ, maxX - width, topLevel, minZ + width);

        generateBox(level, box, minX + width, minY, maxZ - width, maxX - width, topLevel, maxZ, false, random, stepBlocks);
        generateSusBlocks(level, box, minX + width, topLevel, maxZ - width, maxX - width, topLevel, maxZ);

        generateBox(level, box, minX, minY, minZ + width, minX + width, topLevel, maxZ - width, false, random, stepBlocks);
        generateSusBlocks(level, box, minX, topLevel, minZ + width, minX + width, topLevel, maxZ - width);

        generateBox(level, box, maxX - width, minY, minZ + width, maxX, topLevel, maxZ - width, false, random, stepBlocks);
        generateSusBlocks(level, box, maxX - width, topLevel, minZ + width, maxX, topLevel, maxZ - width);
    }

    protected void generateSusBlocks(WorldGenLevel level, BoundingBox box, int x1, int y1, int z1, int x2, int y2, int z2) {
        for (var tries = 0; tries < 10; tries++) {
            var x = x1 + random.nextInt(Math.max(1, x2 - x1));
            var y = y1 + random.nextInt(Math.max(1, y2 - y1));
            var z = z1 + random.nextInt(Math.max(1, z2 - z1));
            var pos = getWorldPos(x, y, z);

            if (random.nextFloat() < 0.2f) {
                placeBlock(level, Blocks.GRAVEL.defaultBlockState(), x, y, z, box);
                continue;
            }

            if (box.isInside(pos)) {
                level.setBlock(pos, Blocks.SUSPICIOUS_GRAVEL.defaultBlockState(), 2);
                if (level.getBlockEntity(pos) instanceof BrushableBlockEntity brushable) {
                    brushable.setLootTable(BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY, random.nextLong());
                }
            }
        }
    }

    protected void generateDangerSculk(WorldGenLevel level, BoundingBox box, int x1, int y1, int z1, int x2, int y2, int z2) {
        for (var tries = 0; tries < 20; tries++) {
            var x = x1 + random.nextInt(Math.max(1, x2 - x1));
            var y = y1 + random.nextInt(Math.max(1, y2 - y1));
            var z = z1 + random.nextInt(Math.max(1, z2 - z1));
            var pos = getWorldPos(x, y, z);

            if (random.nextFloat() < 0.1f) {
                continue;
            }

                placeBlock(level, Blocks.MAGENTA_WOOL.defaultBlockState(), x, y + 1, z, box);
//                if (random.nextBoolean()) {
//                    var shrieker = Blocks.SCULK_SHRIEKER.defaultBlockState().setValue(SculkShriekerBlock.CAN_SUMMON, true);
//                    placeBlock(level, shrieker, x, y + 1, z, box);
//                } else {
//                    var catalyst = Blocks.SCULK_CATALYST.defaultBlockState();
//                    placeBlock(level, catalyst, x, y, z, box);
//                }
        }
    }

    protected abstract class AmphitheaterBlockSelector extends BlockSelector {
        protected Optional<BlockState> tryDecay() {
            if (random.nextFloat() < Amphitheater.this.airDecay) {
                return Optional.of(CAVE_AIR);
            }

            if (random.nextFloat() < Amphitheater.this.sculkDecay) {
                for (var entry : Amphitheater.this.sculkBlockMap.entrySet()) {
                    if (random.nextFloat() < entry.getValue()) {
                        return Optional.of(entry.getKey());
                    }
                }
            }

            return Optional.empty();
        }
    }

    protected class StepBlockSelector extends AmphitheaterBlockSelector {
        @Override
        public void next(RandomSource random, int x, int y, int z, boolean bl) {
            var opt = tryDecay();
            if (opt.isPresent()) {
                this.next = opt.get();
                return;
            }

            for (var entry : Amphitheater.this.stepBlockMap.entrySet()) {
                if (random.nextFloat() < entry.getValue()) {
                    this.next = entry.getKey();
                    return;
                }
                random.nextFloat();
            }

            this.next = CAVE_AIR;
        }
    }

    protected class PillarBlockSelector extends AmphitheaterBlockSelector {
        @Override
        public void next(RandomSource random, int x, int y, int z, boolean bl) {
            var opt = tryDecay();
            if (opt.isPresent()) {
                this.next = opt.get();
                return;
            }

            for (var entry : Amphitheater.this.pillarBlockMap.entrySet()) {
                if (random.nextFloat() < entry.getValue()) {
                    this.next = entry.getKey();
                    return;
                }
                random.nextFloat();
            }

            this.next = CAVE_AIR;
        }
    }
}
