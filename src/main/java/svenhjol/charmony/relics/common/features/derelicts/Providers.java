package svenhjol.charmony.relics.common.features.derelicts;

import svenhjol.charmony.api.Api;
import svenhjol.charmony.core.base.Setup;
import svenhjol.charmony.relics.common.features.derelicts.providers.DerelictDefinitionProviders;
import svenhjol.charmony.relics.common.features.derelicts.providers.RunestoneDefinitionProviders;
import svenhjol.charmony.relics.common.features.derelicts.providers.SecretChestDefinitionProviders;

public class Providers extends Setup<Derelicts> {
    public Providers(Derelicts feature) {
        super(feature);

        Api.registerProvider(new DerelictDefinitionProviders());
        Api.registerProvider(new RunestoneDefinitionProviders());
        Api.registerProvider(new SecretChestDefinitionProviders());
    }
}
