package svenhjol.charmony.relics.common.features.relics;

import net.minecraft.Util;
import net.minecraft.world.item.ItemStack;
import svenhjol.charmony.api.relics.RelicDefinition;
import svenhjol.charmony.api.relics.RelicDefinitionProvider;
import svenhjol.charmony.api.relics.RelicType;
import svenhjol.charmony.api.relics.RelicsApi;
import svenhjol.charmony.core.Api;
import svenhjol.charmony.core.base.Setup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Registers extends Setup<Relics> {
    public final Map<String, RelicDefinition> relicDefinitions = new HashMap<>();
    public final Map<RelicType, List<RelicDefinition>> relicsByType = new HashMap<>();

    public Registers(Relics feature) {
        super(feature);

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
                return feature().handlers.createRelicItem(definition, registry, random);
            });

            // Implementation to get a random relic from the available pool.
            RelicsApi.instance().setRandomRelicImpl((registry, random) -> {
                var definitions = new ArrayList<>(relicDefinitions.values());
                if (definitions.isEmpty()) return ItemStack.EMPTY;

                Util.shuffle(definitions, random);
                var definition = definitions.getFirst();
                return feature().handlers.createRelicItem(definition, registry, random);
            });

            // Implementation to get a random relic of a given type.
            RelicsApi.instance().setRandomRelicOfTypeImpl((registry, random, type) -> {
                var definitions = new ArrayList<>(relicsByType.get(type));
                if (definitions.isEmpty()) return ItemStack.EMPTY;

                Util.shuffle(definitions, random);
                var definition = definitions.getFirst();
                return feature().handlers.createRelicItem(definition, registry, random);
            });
        };
    }
}
