package svenhjol.charmony.relics.common.features.derelicts;

import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import svenhjol.charmony.core.base.Setup;
import svenhjol.charmony.core.helpers.TagHelper;
import svenhjol.charmony.relics.common.features.derelicts.loot_functions.DerelictMapFunction;
import svenhjol.charmony.relics.common.features.relics.Relics;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Handlers extends Setup<Derelicts> {
    public Handlers(Derelicts feature) {
        super(feature);
    }

    public void handleLootTableModify(ResourceKey<LootTable> key, LootTable.Builder builder, LootTableSource source, HolderLookup.Provider provider) {
        if (!source.isBuiltin()) return;

        // Add enchanted tomes to derelict books chests.
        if (key == Tags.DERELICT_BOOKS_CHEST) {
            Relics.feature().handlers.addBookToLootWithChance(builder, feature().booksChestChance());
        }

        // Add derelict maps to ancient city loot.
        if (key == BuiltInLootTables.ANCIENT_CITY) {
            var pool = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .when(LootItemRandomChanceCondition.randomChance((float)feature().mapLootChance()))
                .add(LootItem.lootTableItem(Items.MAP)
                    .setWeight(1)
                    .apply(() -> new DerelictMapFunction(List.of())));

            builder.pool(pool.build());
        }
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

    public Optional<ItemStack> createMap(ServerLevel serverLevel, BlockPos pos) {
        var target = serverLevel.findNearestMapStructure(Tags.ON_DERELICT_MAPS, pos, 100, true);
        if (target != null) {
            var map = MapItem.create(serverLevel, target.getX(), target.getZ(), (byte)2, true, true);
            MapItem.renderBiomePreviewMap(serverLevel, map);
            MapItemSavedData.addTargetDecoration(map, target, "+", MapDecorationTypes.TARGET_X);
            map.set(DataComponents.ITEM_NAME, Component.translatable("filled_map.charmony.derelict"));
            return Optional.of(map);
        }
        return Optional.empty();
    }
}
