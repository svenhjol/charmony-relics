package svenhjol.charmony.relics.common.features.derelicts.providers;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import svenhjol.charmony.api.runestones.RunestoneDefinition;
import svenhjol.charmony.api.runestones.RunestoneDefinitionProvider;
import svenhjol.charmony.api.runestones.RunestoneLocation;
import svenhjol.charmony.api.runestones.RunestoneType;
import svenhjol.charmony.core.helpers.ItemStackHelper;
import svenhjol.charmony.core.helpers.TagHelper;
import svenhjol.charmony.relics.common.features.derelicts.Derelicts;
import svenhjol.charmony.relics.common.features.derelicts.Tags;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class RunestoneDefinitionProviders implements RunestoneDefinitionProvider {
    @Override
    public List<RunestoneDefinition> getRunestoneDefinitions() {
        return List.of(derelict());
    }

    private RunestoneDefinition derelict() {
        return new RunestoneDefinition() {
            @Override
            public RunestoneType type() {
                return RunestoneType.Stone;
            }

            @Override
            public Supplier<? extends Block> baseBlock() {
                return () -> Blocks.CRACKED_STONE_BRICKS;
            }

            @Override
            public Optional<RunestoneLocation> location(LevelAccessor level, BlockPos pos, RandomSource random, double quality) {
                if (feature().enabled() && random.nextDouble() < feature().runestoneChance()) {
                    var registry = level.registryAccess().lookupOrThrow(Registries.STRUCTURE);
                    var derelicts = TagHelper.getValues(registry, Tags.RUNESTONE_LOCATED)
                        .stream().map(registry::getKey).toList();
                    if (!derelicts.isEmpty()) {
                        var derelict = derelicts.get(random.nextInt(derelicts.size()));
                        return Optional.of(new RunestoneLocation(RunestoneLocation.Type.Structure, derelict));
                    }
                }
                return Optional.empty();
            }

            @Override
            public Supplier<ItemLike> sacrifice(LevelAccessor level, BlockPos pos, RandomSource random, double quality) {
                return () -> ItemStackHelper.randomItem(level, random, Tags.DERELICT_RUNESTONE, Items.DIAMOND);
            }
        };
    }

    private static Derelicts feature() {
        return Derelicts.feature();
    }
}
