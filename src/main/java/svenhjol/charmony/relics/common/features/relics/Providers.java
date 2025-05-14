package svenhjol.charmony.relics.common.features.relics;

import svenhjol.charmony.api.Api;
import svenhjol.charmony.api.relics.RelicDefinition;
import svenhjol.charmony.api.relics.RelicDefinitionProvider;
import svenhjol.charmony.core.base.Setup;
import svenhjol.charmony.relics.common.features.relics.items.*;

import java.util.List;

public class Providers extends Setup<Relics> implements RelicDefinitionProvider {
    public Providers(Relics feature) {
        super(feature);
        Api.registerProvider(this);
    }

    @Override
    public List<RelicDefinition> getRelicDefinitions() {
        return List.of(
            new ArcaneAxeRelic(),
            new ArcaneBowRelic(),
            new ArcaneChestplateRelic(),
            new ArcaneCrossbowRelic(),
            new ArcaneSwordRelic(),
            new DiamondAxeRelic(),
            new DiamondBootsRelic(),
            new DiamondChestplateRelic(),
            new DiamondHelmetRelic(),
            new DiamondLeggingsRelic(),
            new DiamondPickaxeRelic(),
            new DiamondShovelRelic(),
            new DiamondSwordRelic(),
            new EnchantedTomeRelic(),
            new FishingRodRelic(),
            new ShieldRelic(),
            new TridentRelic()
        );
    }
}
