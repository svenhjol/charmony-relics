package svenhjol.charmony.relics.common.features.relics;

import svenhjol.charmony.api.Api;
import svenhjol.charmony.api.relics.RelicDefinition;
import svenhjol.charmony.api.relics.RelicDefinitionProvider;
import svenhjol.charmony.core.base.Setup;
import svenhjol.charmony.relics.common.features.relics.items.ArcaneBowRelic;
import svenhjol.charmony.relics.common.features.relics.items.DiamondAxeRelic;
import svenhjol.charmony.relics.common.features.relics.items.DiamondPickaxeRelic;
import svenhjol.charmony.relics.common.features.relics.items.EnchantedTomeRelic;

import java.util.List;

public class Providers extends Setup<Relics> implements RelicDefinitionProvider {
    public Providers(Relics feature) {
        super(feature);
        Api.registerProvider(this);
    }

    @Override
    public List<RelicDefinition> getRelicDefinitions() {
        return List.of(
            new ArcaneBowRelic(),
            new DiamondAxeRelic(),
            new DiamondPickaxeRelic(),
            new EnchantedTomeRelic()
        );
    }
}
