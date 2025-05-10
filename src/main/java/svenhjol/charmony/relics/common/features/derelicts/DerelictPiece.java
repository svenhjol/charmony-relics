package svenhjol.charmony.relics.common.features.derelicts;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import svenhjol.charmony.api.DerelictDefinition;

public abstract class DerelictPiece extends StructurePiece {
    protected DerelictDefinition definition;

    protected DerelictPiece(StructurePieceType type, DerelictDefinition definition, int genDepth, BoundingBox boundingBox) {
        super(type, genDepth, boundingBox);
        this.definition = definition;
    }

    public DerelictPiece(StructurePieceType type, CompoundTag tag) {
        super(type, tag);
        this.definition = feature().registers.definitions.get(tag.getStringOr(Constants.DEFINITION_TAG, "simple"));
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        tag.putString(Constants.DEFINITION_TAG, definition.getSerializedName());
    }

    protected static Derelicts feature() {
        return Derelicts.feature();
    }
}
