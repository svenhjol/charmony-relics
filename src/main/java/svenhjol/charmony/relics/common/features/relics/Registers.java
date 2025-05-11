package svenhjol.charmony.relics.common.features.relics;

import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import svenhjol.charmony.api.relics.RelicDefinition;
import svenhjol.charmony.api.relics.RelicDefinitionProvider;
import svenhjol.charmony.api.relics.RelicType;
import svenhjol.charmony.api.relics.RelicsApi;
import svenhjol.charmony.core.Api;
import svenhjol.charmony.core.base.Registerable;
import svenhjol.charmony.core.base.Setup;
import svenhjol.charmony.relics.common.features.relics.loot_functions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class Registers extends Setup<Relics> {
    public final Map<String, RelicDefinition> relicDefinitions = new HashMap<>();
    public final Map<RelicType, List<RelicDefinition>> relicsByType = new HashMap<>();
    public final Map<String, Supplier<LootItemFunctionType<? extends LootItemConditionalFunction>>> lootFunctions = new HashMap<>();

    public Registers(Relics feature) {
        super(feature);

        // Loot functions for adding relics to loot tables.
        lootFunctions.put(Constants.RELIC_LOOT, new Registerable<>(feature, () -> Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE,
            feature.id(Constants.RELIC_LOOT), new LootItemFunctionType<>(RelicLootFunction.CODEC))));

        lootFunctions.put(Constants.WEAPON_LOOT, new Registerable<>(feature, () -> Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE,
            feature.id(Constants.WEAPON_LOOT), new LootItemFunctionType<>(WeaponLootFunction.CODEC))));

        lootFunctions.put(Constants.TOOL_LOOT, new Registerable<>(feature, () -> Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE,
            feature.id(Constants.TOOL_LOOT), new LootItemFunctionType<>(ToolLootFunction.CODEC))));

        lootFunctions.put(Constants.ARMOR_LOOT, new Registerable<>(feature, () -> Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE,
            feature.id(Constants.ARMOR_LOOT), new LootItemFunctionType<>(ArmorLootFunction.CODEC))));

        lootFunctions.put(Constants.BOOK_LOOT, new Registerable<>(feature, () -> Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE,
            feature.id(Constants.BOOK_LOOT), new LootItemFunctionType<>(BookLootFunction.CODEC))));

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
            RelicsApi.instance().setRelicImpl((id, registry, random) -> {
                var definition = relicDefinitions.getOrDefault(id, null);
                if (definition == null) return ItemStack.EMPTY;
                return definition.relicItem(registry, random);
            });

            // Implementation to get a random relic from the available pool.
            RelicsApi.instance().setRandomRelicImpl((registry, random) -> {
                var definitions = new ArrayList<>(relicDefinitions.values());
                if (definitions.isEmpty()) return ItemStack.EMPTY;

                Util.shuffle(definitions, random);
                var definition = definitions.getFirst();
                return definition.relicItem(registry, random);
            });

            // Implementation to get a random relic of a given type.
            RelicsApi.instance().setRandomRelicOfTypeImpl((registry, random, type) -> {
                var definitions = new ArrayList<>(relicsByType.get(type));
                if (definitions.isEmpty()) return ItemStack.EMPTY;

                Util.shuffle(definitions, random);
                var definition = definitions.getFirst();
                return definition.relicItem(registry, random);
            });
        };
    }
}
