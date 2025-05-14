package svenhjol.charmony.relics.common.features.derelicts.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import svenhjol.charmony.api.derelicts.DerelictDefinition;
import svenhjol.charmony.api.secret_chests.SecretChestsApi;
import svenhjol.charmony.relics.common.features.derelicts.Constants;
import svenhjol.charmony.relics.common.features.derelicts.DerelictPiece;
import svenhjol.charmony.relics.common.features.derelicts.providers.SecretChestDefinitionProviders;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class Amphitheater extends DerelictPiece {
    private final RandomSource random;
    private float airDecay = 0.0f;
    private float sculkDecay = 0.0f;

    private final Map<BlockState, Float> stepBlockMap = new LinkedHashMap<>();
    private final Map<BlockState, Float> pillarBlockMap = new LinkedHashMap<>();
    private final Map<BlockState, Float> wallBlockMap = new LinkedHashMap<>();
    private final Map<BlockState, Float> sculkBlockMap = new LinkedHashMap<>();

    private final WallBlockSelector wallBlocks = new WallBlockSelector();
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
        wallBlockMap.clear();

        stepBlockMap.put(Blocks.POLISHED_DEEPSLATE.defaultBlockState(), 0.7f);
        stepBlockMap.put(Blocks.DEEPSLATE_BRICKS.defaultBlockState(), 0.7f);
        stepBlockMap.put(Blocks.CRACKED_DEEPSLATE_BRICKS.defaultBlockState(), 0.6f);
        stepBlockMap.put(Blocks.GRAVEL.defaultBlockState(), 0.4f);
        stepBlockMap.put(Blocks.DEEPSLATE_TILES.defaultBlockState(), 0.4f);
        stepBlockMap.put(Blocks.COBBLED_DEEPSLATE.defaultBlockState(), 1.0f);

        pillarBlockMap.put(Blocks.DEEPSLATE_BRICKS.defaultBlockState(), 0.64f);
        pillarBlockMap.put(Blocks.CRACKED_DEEPSLATE_BRICKS.defaultBlockState(), 0.7f);
        pillarBlockMap.put(Blocks.DEEPSLATE_TILES.defaultBlockState(), 0.84f);
        pillarBlockMap.put(Blocks.GRAVEL.defaultBlockState(), 0.1f);
        pillarBlockMap.put(Blocks.COBBLED_DEEPSLATE.defaultBlockState(), 1.0f);
;
        wallBlockMap.put(Blocks.DEEPSLATE_TILES.defaultBlockState(), 0.5f);
        wallBlockMap.put(Blocks.CRACKED_DEEPSLATE_TILES.defaultBlockState(), 0.5f);
        wallBlockMap.put(Blocks.DEEPSLATE_BRICKS.defaultBlockState(), 0.75f);
        wallBlockMap.put(Blocks.CRACKED_DEEPSLATE_BRICKS.defaultBlockState(), 0.75f);
        wallBlockMap.put(Blocks.COBBLED_DEEPSLATE.defaultBlockState(), 0.8f);
        wallBlockMap.put(Blocks.GRAVEL.defaultBlockState(), 1.0f);

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

        // Clear everything we will generate in.
        generateBox(level, box, minX, minY, minZ, maxX, minY + 10, maxZ, CAVE_AIR, CAVE_AIR, false);

        // Make a dome above this space
        generateUpperHalfSphere(level, box, minX, minY + 10, minZ, maxX, maxY, maxZ, CAVE_AIR, false);

        // Create walls to prevent weird lava behavior
        airDecay = 0f;
        sculkDecay = 0.15f;
        var wallHeight = maxY - minY + 2;
        generateMirroredWalls(level, box, wallHeight);

        // Create main corner pillars
        airDecay = 0f;
        sculkDecay = 0.05f;
        var pillarHeight = maxY - minY + 2;
        generateCornerPillars(level, box, 0, 12, pillarHeight);

        // Generate slabs on top of the pillars
        airDecay = 0.01f;
        sculkDecay = 0.12f;
        generateSingleSlab(level, box, minX, minY + pillarHeight, minZ, 12, 2);
        generateSingleSlab(level, box, maxX - 12, minY + pillarHeight, minZ, 12, 2);
        generateSingleSlab(level, box, minX, minY + pillarHeight, maxZ - 12, 12, 2);
        generateSingleSlab(level, box, maxX - 12, minY + pillarHeight, maxZ - 12, 12, 2);

        // Create main pillar walkways and wall shelves
        airDecay = 0f;
        sculkDecay = 0.0f;
        generateMirroredSteps(level, box, 0, 10, 5, 8, 4, false);
        generateMirroredShelves(level, box, 7);

        // Create steps leading down into the pit
        airDecay = 0.0005f;
        sculkDecay = 0.01f;
        generateMirroredSteps(level, box, 10, 2, 4, 0, 1, false);
        generateMirroredSteps(level, box, 12, 2, 3, 1, 0, true);
        generateMirroredSteps(level, box, 14, 2, 2, 2, 0, true);
        generateMirroredSteps(level, box, 16, 2, 1, 3, 0, false);

        // Create supporting pillars
        airDecay = 0.01f;
        sculkDecay = 0.0f;
        generateCornerPillars(level, box, 11, 1, 5);
        generateCornerPillars(level, box, 13, 1, 4);
        generateCornerPillars(level, box, 15, 1, 3);
        generateCornerPillars(level, box, 17, 1, 2);
        generateCornerPillars(level, box, 19, 1, 1);

        // Create the base slab.
        var width = (maxX - minX);
        generateSingleSlab(level, box, minX + 17, minY, minZ + 17, width, 0);
        tryGenerateBrushables(level, box, minX + 17, minY, minZ + 17, maxX - 17, minY, maxZ - 17, 12);
        generateDangerSculk(level, box, minX + 17, minY, minZ + 17, maxX - 17, minY, maxZ - 17);
    }

    protected void generateCornerPillars(WorldGenLevel level, BoundingBox box, int wallOffset, int width, int height) {
        var minX = boundingBox.minX() + wallOffset;
        var minY = boundingBox.minY();
        var minZ = boundingBox.minZ() + wallOffset;
        var maxX = boundingBox.maxX() - wallOffset;
        var maxZ = boundingBox.maxZ() - wallOffset;

        generateBox(level, box, minX, minY, minZ, minX + width, minY + height, minZ + width, false, random, pillarBlocks);
        generateBox(level, box, maxX - width, minY, minZ, maxX, minY + height, minZ + width, false, random, pillarBlocks);
        generateBox(level, box, minX, minY, maxZ - width, minX + width, minY + height, maxZ, false, random, pillarBlocks);
        generateBox(level, box, maxX - width, minY, maxZ - width, maxX, minY + height, maxZ, false, random, pillarBlocks);
    }

    protected void generateMirroredWalls(WorldGenLevel level, BoundingBox box, int height) {
        var minX = boundingBox.minX();
        var minY = boundingBox.minY();
        var minZ = boundingBox.minZ();
        var maxX = boundingBox.maxX();
        var maxZ = boundingBox.maxZ();

        var width = 1;

        generateBox(level, box, minX, minY, minZ, minX + width, minY + height, maxZ, false, random, wallBlocks);
        generateBox(level, box, minX, minY, minZ, maxX, minY + height, minZ + width, false, random, wallBlocks);
        generateBox(level, box, minX, minY, maxZ - width, maxX, minY + height, maxZ, false, random, wallBlocks);
        generateBox(level, box, maxX - width, minY, minZ, maxX, minY + height, maxZ, false, random, wallBlocks);
    }

    protected void generateMirroredShelves(WorldGenLevel level, BoundingBox box, int maxPots) {
        var minX = boundingBox.minX();
        var minY = boundingBox.minY();
        var minZ = boundingBox.minZ();
        var maxX = boundingBox.maxX();
        var maxZ = boundingBox.maxZ();

        var width = 2;
        var shelfY = minY + 8;

        generateBox(level, box, minX + width, shelfY, minZ, minX + width, shelfY, maxZ, false, random, stepBlocks);
        if (maxPots > 0) {
            tryGeneratePots(level, box, minX + width, shelfY, minZ, minX + width, shelfY, maxZ, maxPots);
        }

        generateBox(level, box, minX, shelfY, minZ + width, maxX, shelfY, minZ + width, false, random, stepBlocks);
        if (maxPots > 0) {
            tryGeneratePots(level, box, minX, shelfY, minZ + width, maxX, shelfY, minZ + width, maxPots);
        }

        generateBox(level, box, minX, shelfY, maxZ - width, maxX, shelfY, maxZ - width, false, random, stepBlocks);
        if (maxPots > 0) {
            tryGeneratePots(level, box, minX, shelfY, maxZ - width, maxX, shelfY, maxZ - width, maxPots);
        }

        generateBox(level, box, maxX - width, shelfY, minZ, maxX + width, shelfY, maxZ, false, random, stepBlocks);
        if (maxPots > 0) {
            tryGeneratePots(level, box, maxX - width, shelfY, minZ, maxX + width, shelfY, maxZ, maxPots);
        }

    }

    protected void generateMirroredSteps(WorldGenLevel level, BoundingBox box, int wallOffset, int width, int height, int maxBrushables, int maxPots, boolean withChest) {
        var minX = boundingBox.minX() + wallOffset;
        var minY = boundingBox.minY();
        var minZ = boundingBox.minZ() + wallOffset;
        var maxX = boundingBox.maxX() - wallOffset;
        var maxZ = boundingBox.maxZ() - wallOffset;

        var topLevel = minY + height;
        var generatedChest = false;

        generateBox(level, box, minX + width, minY, minZ, maxX - width, topLevel, minZ + width, false, random, stepBlocks);
        if (maxBrushables > 0) {
            tryGenerateBrushables(level, box, minX + width, topLevel, minZ, maxX - width, topLevel, minZ + width, maxBrushables);
        }
        if (maxPots > 0) {
            tryGeneratePots(level, box, minX + width, topLevel, minZ, maxX - width, topLevel, minZ + width, maxPots);
        }
        if (withChest) {
            generatedChest = tryGenerateChest(level, box, minX + width, topLevel, minZ, maxX - width, topLevel, minZ + width, Direction.SOUTH);
        }

        generateBox(level, box, minX + width, minY, maxZ - width, maxX - width, topLevel, maxZ, false, random, stepBlocks);
        if (maxBrushables > 0) {
            tryGenerateBrushables(level, box, minX + width, topLevel, maxZ - width, maxX - width, topLevel, maxZ, maxBrushables);
        }
        if (maxPots > 0) {
            tryGeneratePots(level, box, minX + width, topLevel, maxZ - width, maxX - width, topLevel, maxZ, maxPots);
        }
        if (withChest && !generatedChest) {
            generatedChest = tryGenerateChest(level, box, minX + width, topLevel, maxZ - width, maxX - width, topLevel, maxZ, Direction.NORTH);
        }

        generateBox(level, box, minX, minY, minZ + width, minX + width, topLevel, maxZ - width, false, random, stepBlocks);
        if (maxBrushables > 0) {
            tryGenerateBrushables(level, box, minX, topLevel, minZ + width, minX + width, topLevel, maxZ - width, maxBrushables);
        }
        if (maxPots > 0) {
            tryGeneratePots(level, box, minX, topLevel, minZ + width, minX + width, topLevel, maxZ - width, maxPots);
        }
        if (withChest && !generatedChest) {
            generatedChest = tryGenerateChest(level, box, minX, topLevel, minZ + width, minX + width, topLevel, maxZ - width, Direction.EAST);
        }

        generateBox(level, box, maxX - width, minY, minZ + width, maxX, topLevel, maxZ - width, false, random, stepBlocks);
        if (maxBrushables > 0) {
            tryGenerateBrushables(level, box, maxX - width, topLevel, minZ + width, maxX, topLevel, maxZ - width, maxBrushables);
        }
        if (maxPots > 0) {
            tryGeneratePots(level, box, maxX - width, topLevel, minZ + width, maxX, topLevel, maxZ - width, maxPots);
        }
        if (withChest && !generatedChest) {
            tryGenerateChest(level, box, maxX - width, topLevel, minZ + width, maxX, topLevel, maxZ - width, Direction.WEST);
        }
    }

    protected void generateSingleSlab(WorldGenLevel level, BoundingBox box, int x, int y, int z, int width, int height) {
        generateBox(level, box, x, y, z, x + width, y + height, z + width, false, random, stepBlocks);
    }

    protected void tryGenerateBrushables(WorldGenLevel level, BoundingBox box, int x1, int y1, int z1, int x2, int y2, int z2, int tries) {
        for (var i = 0; i < tries; i++) {
            var x = x1 + random.nextInt(Math.max(1, x2 - x1));
            var y = y1 + random.nextInt(Math.max(1, y2 - y1));
            var z = z1 + random.nextInt(Math.max(1, z2 - z1));
            var pos = getWorldPos(x, y, z);

            if (random.nextFloat() < 0.5f) {
                placeBlock(level, Blocks.GRAVEL.defaultBlockState(), x, y, z, box);
                continue;
            }

            if (box.isInside(pos) && level.getBlockState(pos.below()).isSolidRender()) {
                level.setBlock(pos, Blocks.SUSPICIOUS_GRAVEL.defaultBlockState(), 2);
                if (level.getBlockEntity(pos) instanceof BrushableBlockEntity brushable) {
                    brushable.setLootTable(BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY, random.nextLong());
                }
            }
        }
    }

    protected void tryGeneratePots(WorldGenLevel level, BoundingBox box, int x1, int y1, int z1, int x2, int y2, int z2, int tries) {
        for (var i = 0; i < tries; i++) {
            var x = x1 + random.nextInt(Math.max(1, x2 - x1));
            var y = y1 + random.nextInt(Math.max(1, y2 - y1));
            var z = z1 + random.nextInt(Math.max(1, z2 - z1));
            var pos = getWorldPos(x, y + 1, z);
            var state = level.getBlockState(pos);
            var stateBelow = level.getBlockState(pos.below());

            if (random.nextFloat() < 0.12f) {
                continue;
            }

            if (box.isInside(pos) && state.isAir() && stateBelow.isSolidRender()) {
                feature().handlers.createDecoratedPot(level, pos, random, BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY);
            }
        }
    }

    protected boolean tryGenerateChest(WorldGenLevel level, BoundingBox box, int x1, int y1, int z1, int x2, int y2, int z2, Direction facing) {
        var x = x1 + random.nextInt(Math.max(1, x2 - x1));
        var y = y1 + random.nextInt(Math.max(1, y2 - y1));
        var z = z1 + random.nextInt(Math.max(1, z2 - z1));
        var pos = getWorldPos(x, y + 1, z);
        var debugGenerateBase = false;

        if (random.nextFloat() < 0.1f) {
            return false;
        }

        if (box.isInside(pos)) {
            //noinspection ConstantValue
            if (debugGenerateBase) {
                switch (facing) {
                    case NORTH -> placeBlock(level, Blocks.LIGHT_BLUE_WOOL.defaultBlockState(), x, y, z, box);
                    case SOUTH -> placeBlock(level, Blocks.WHITE_WOOL.defaultBlockState(), x, y, z, box);
                    case EAST -> placeBlock(level, Blocks.ORANGE_WOOL.defaultBlockState(), x, y, z, box);
                    case WEST -> placeBlock(level, Blocks.MAGENTA_WOOL.defaultBlockState(), x, y, z, box);
                }
            }
            return SecretChestsApi.instance().createChest(SecretChestDefinitionProviders.DERELICT, level, random, pos, false, facing);
        }

        return false;
    }

    protected void generateDangerSculk(WorldGenLevel level, BoundingBox box, int x1, int y1, int z1, int x2, int y2, int z2) {
        for (var tries = 0; tries < 4; tries++) {
            var x = x1 + random.nextInt(Math.max(1, x2 - x1));
            var y = y1 + random.nextInt(Math.max(1, y2 - y1));
            var z = z1 + random.nextInt(Math.max(1, z2 - z1));
            var pos = getWorldPos(x, y, z);

            if (random.nextFloat() < 0.1f) {
                continue;
            }

            if (box.isInside(pos)) {
                if (random.nextFloat() < 0.5f) {
                    var shrieker = Blocks.SCULK_SHRIEKER.defaultBlockState().setValue(SculkShriekerBlock.CAN_SUMMON, true);
                    placeBlock(level, shrieker, x, y + 1, z, box);
                } else if (random.nextFloat() < 0.4f){
                    var catalyst = Blocks.SCULK_CATALYST.defaultBlockState();
                    placeBlock(level, catalyst, x, y, z, box);
                } else {
                    var sensor = Blocks.SCULK_SENSOR.defaultBlockState();
                    placeBlock(level, sensor, x, y + 1, z, box);
                }
            }
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

        public abstract Map<BlockState, Float> blockMap();

        @Override
        public void next(RandomSource randomSource, int x, int y, int z, boolean bl) {
            var opt = tryDecay();
            if (opt.isPresent()) {
                this.next = opt.get();
                return;
            }

            for (var entry : blockMap().entrySet()) {
                if (random.nextFloat() < entry.getValue()) {
                    this.next = entry.getKey();
                    return;
                }
                random.nextFloat();
            }

            this.next = CAVE_AIR;
        }
    }

    protected class StepBlockSelector extends AmphitheaterBlockSelector {
        @Override
        public Map<BlockState, Float> blockMap() {
            return Amphitheater.this.stepBlockMap;
        }
    }

    protected class PillarBlockSelector extends AmphitheaterBlockSelector {
        @Override
        public Map<BlockState, Float> blockMap() {
            return Amphitheater.this.pillarBlockMap;
        }
    }

    protected class WallBlockSelector extends AmphitheaterBlockSelector {
        @Override
        public Map<BlockState, Float> blockMap() {
            return Amphitheater.this.wallBlockMap;
        }
    }
}
