package svenhjol.charmony.relics.common.features.derelicts;

import svenhjol.charmony.core.annotations.FeatureDefinition;
import svenhjol.charmony.core.base.Mod;
import svenhjol.charmony.core.base.SidedFeature;
import svenhjol.charmony.core.enums.Side;
import svenhjol.charmony.relics.common.features.derelicts.providers.DerelictDefinitionProviders;
import svenhjol.charmony.relics.common.features.derelicts.providers.SecretChestDefinitionProviders;

@FeatureDefinition(side = Side.Common)
public final class Derelicts extends SidedFeature {
    public final Registers registers;
    public final Handlers handlers;

    public final DerelictDefinitionProviders derelictDefinitionProviders;
    public final SecretChestDefinitionProviders secretChestDefinitionProviders;

    public Derelicts(Mod mod) {
        super(mod);
        registers = new Registers(this);
        handlers = new Handlers(this);
        derelictDefinitionProviders = new DerelictDefinitionProviders(this);
        secretChestDefinitionProviders = new SecretChestDefinitionProviders(this);
    }

    public static Derelicts feature() {
        return Mod.getSidedFeature(Derelicts.class);
    }
}
