package svenhjol.charmony.relics.common.features.relics;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.Util;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import svenhjol.charmony.api.events.PlayerTickCallback;
import svenhjol.charmony.api.relics.RelicDefinition;
import svenhjol.charmony.api.relics.RelicDefinitionProvider;
import svenhjol.charmony.api.relics.RelicType;
import svenhjol.charmony.api.relics.RelicsApi;
import svenhjol.charmony.core.Api;
import svenhjol.charmony.core.base.Setup;
import svenhjol.charmony.core.common.CommonRegistry;
import svenhjol.charmony.relics.common.features.relics.loot_functions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class Registers extends Setup<Relics> {
    public final Supplier<DataComponentType<RelicData>> data;
    public final Map<String, RelicDefinition> relicDefinitions = new HashMap<>();
    public final Map<RelicType, List<RelicDefinition>> relicsByType = new HashMap<>();
    public final Map<String, Supplier<LootItemFunctionType<? extends LootItemConditionalFunction>>> lootFunctions = new HashMap<>();

    public Registers(Relics feature) {
        super(feature);

        var registry = CommonRegistry.forFeature(feature);

        data = registry.dataComponent("relic",
            () -> builder -> builder
                .persistent(RelicData.CODEC)
                .networkSynchronized(RelicData.STREAM_CODEC));

        // Loot functions for adding relics to loot tables.
        lootFunctions.put(Constants.RELIC_LOOT, registry.lootFunctionType(Constants.RELIC_LOOT,
            () -> new LootItemFunctionType<>(RelicLootFunction.CODEC)));

        lootFunctions.put(Constants.WEAPON_LOOT, registry.lootFunctionType(Constants.WEAPON_LOOT,
            () -> new LootItemFunctionType<>(WeaponLootFunction.CODEC)));

        lootFunctions.put(Constants.TOOL_LOOT, registry.lootFunctionType(Constants.TOOL_LOOT,
            () -> new LootItemFunctionType<>(ToolLootFunction.CODEC)));

        lootFunctions.put(Constants.ARMOR_LOOT, registry.lootFunctionType(Constants.ARMOR_LOOT,
            () -> new LootItemFunctionType<>(ArmorLootFunction.CODEC)));

        lootFunctions.put(Constants.BOOK_LOOT, registry.lootFunctionType(Constants.BOOK_LOOT,
            () -> new LootItemFunctionType<>(BookLootFunction.CODEC)));

        Api.consume(RelicDefinitionProvider.class, provider -> {
            relicsByType.clear();
            for (var def : provider.getRelicDefinitions()) {
                relicDefinitions.put(def.id(), def);
                relicsByType.computeIfAbsent(def.type(), a -> new ArrayList<>()).add(def);
            }
        });
    }

    @Override
    public Runnable boot() {
        return () -> {
            // Implementation to get a relic from a given definition ID.
            RelicsApi.Impl.relic((id, registry, random) -> {
                var definition = relicDefinitions.getOrDefault(id, null);
                if (definition == null) return ItemStack.EMPTY;
                return feature().handlers.createRelicItem(definition, registry, random);
            });

            // Implementation to get a random relic from the available pool.
            RelicsApi.Impl.randomRelic((registry, random) -> {
                var definitions = new ArrayList<>(relicDefinitions.values());
                if (definitions.isEmpty()) return ItemStack.EMPTY;

                Util.shuffle(definitions, random);
                var definition = definitions.getFirst();
                return feature().handlers.createRelicItem(definition, registry, random);
            });

            // Implementation to get a random relic of a given type.
            RelicsApi.Impl.randomRelicOfType((registry, random, type) -> {
                var definitions = new ArrayList<>(relicsByType.get(type));
                if (definitions.isEmpty()) return ItemStack.EMPTY;

                Util.shuffle(definitions, random);
                var definition = definitions.getFirst();
                return feature().handlers.createRelicItem(definition, registry, random);
            });

            PlayerTickCallback.EVENT.register(feature().handlers::playerTick);
            LootTableEvents.MODIFY.register(feature().handlers::handleLootTableModify);
        };
    }
}
