package svenhjol.charmony.relics.common.features.relics;

import svenhjol.charmony.api.Api;
import svenhjol.charmony.api.relics.RelicDefinition;
import svenhjol.charmony.api.relics.RelicDefinitionProvider;
import svenhjol.charmony.core.base.Setup;
import svenhjol.charmony.relics.common.features.relics.items.ArcaneBow;
import svenhjol.charmony.relics.common.features.relics.items.EnchantedTome;

import java.util.List;

public class Providers extends Setup<Relics> implements RelicDefinitionProvider {
    public Providers(Relics feature) {
        super(feature);
        Api.registerProvider(this);
    }

    @Override
    public List<RelicDefinition> getRelicDefinitions() {
        return List.of(
            new ArcaneBow(),
            new EnchantedTome()
        );
    }
}
