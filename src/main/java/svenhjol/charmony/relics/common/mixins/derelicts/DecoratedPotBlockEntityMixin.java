package svenhjol.charmony.relics.common.mixins.derelicts;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import svenhjol.charmony.relics.common.features.derelicts.WrappedPot;

@SuppressWarnings("AddedMixinMembersNamePattern")
@Mixin(DecoratedPotBlockEntity.class)
public abstract class DecoratedPotBlockEntityMixin extends BlockEntity implements WrappedPot {
    @Shadow private PotDecorations decorations;

    public DecoratedPotBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Override
    public DecoratedPotBlockEntity setDecorations(PotDecorations decorations) {
        this.decorations = decorations;
        this.setChanged();
        return (DecoratedPotBlockEntity)(Object)this;
    }
}
