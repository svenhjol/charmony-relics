package svenhjol.charmony.relics.common.features.derelicts.providers;

import svenhjol.charmony.api.Api;
import svenhjol.charmony.api.derelicts.DerelictDefinition;
import svenhjol.charmony.api.derelicts.DerelictDefinitionProvider;
import svenhjol.charmony.core.base.Setup;
import svenhjol.charmony.relics.common.features.derelicts.Derelicts;

import java.util.List;

public class DerelictDefinitionProviders extends Setup<Derelicts> implements DerelictDefinitionProvider {
    public static final String AMPHITHEATER = "amphitheater";

    public DerelictDefinitionProviders(Derelicts feature) {
        super(feature);
        Api.registerProvider(this);
    }

    @Override
    public List<DerelictDefinition> getDerelictDefinitions() {
        return List.of(
            new DerelictDefinition() {
                @Override
                public String name() {
                    return AMPHITHEATER;
                }
            }
        );
    }
}
