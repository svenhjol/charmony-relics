package svenhjol.charmony.relics.common.features.derelicts;

import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.PotDecorations;

public interface WrappedPot {
    DecoratedPotBlockEntity setDecorations(PotDecorations decorations);
}
