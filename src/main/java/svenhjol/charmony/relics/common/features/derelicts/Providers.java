package svenhjol.charmony.relics.common.features.derelicts;

import svenhjol.charmony.api.Api;
import svenhjol.charmony.api.DerelictDefinition;
import svenhjol.charmony.api.DerelictDefinitionProvider;
import svenhjol.charmony.core.base.Setup;

import java.util.List;

public class Providers extends Setup<Derelicts> implements DerelictDefinitionProvider {
    public Providers(Derelicts feature) {
        super(feature);
        Api.registerProvider(this);
    }


    @Override
    public List<DerelictDefinition> getDerelictDefinitions() {
        return List.of(
            new DerelictDefinition() {
                @Override
                public String name() {
                    return "amphitheater";
                }
            }
        );
    }
}
