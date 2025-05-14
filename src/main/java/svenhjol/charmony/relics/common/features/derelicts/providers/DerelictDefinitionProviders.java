package svenhjol.charmony.relics.common.features.derelicts.providers;

import svenhjol.charmony.api.derelicts.DerelictDefinition;
import svenhjol.charmony.api.derelicts.DerelictDefinitionProvider;

import java.util.List;

public class DerelictDefinitionProviders implements DerelictDefinitionProvider {
    public static final String AMPHITHEATER = "amphitheater";

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
