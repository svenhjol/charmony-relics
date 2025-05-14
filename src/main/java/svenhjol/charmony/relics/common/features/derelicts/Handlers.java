package svenhjol.charmony.relics.common.features.derelicts;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.level.storage.loot.LootTable;
import svenhjol.charmony.core.base.Setup;
import svenhjol.charmony.core.helpers.TagHelper;

import java.util.ArrayList;
import java.util.List;

public class Handlers extends Setup<Derelicts> {
    public Handlers(Derelicts feature) {
        super(feature);
    }

    public void createDecoratedPot(WorldGenLevel level, BlockPos pos, RandomSource random, ResourceKey<LootTable> lootTable) {
        level.setBlock(pos, Blocks.DECORATED_POT.defaultBlockState(), 2);
        if (level.getBlockEntity(pos) instanceof DecoratedPotBlockEntity pot) {
            PotDecorations decorations;
            var sherds = new ArrayList<>(TagHelper.getValues(level.registryAccess().lookupOrThrow(Registries.ITEM), ItemTags.DECORATED_POT_SHERDS));
            Util.shuffle(sherds, random);
            List<Item> faces = new ArrayList<>(List.of(Items.BRICK, Items.BRICK, Items.BRICK, Items.BRICK));

            if (sherds.size() > 4) {
                for (var j = 0; j < 4; j++) {
                    if (random.nextDouble() < 0.33d) {
                        faces.set(j, sherds.get(j));
                    }
                }
                decorations = new PotDecorations(faces);
            } else {
                decorations = PotDecorations.EMPTY;
            }
            ((WrappedPot)pot).setDecorations(decorations).setLootTable(lootTable, random.nextLong());
        }
    }
}
