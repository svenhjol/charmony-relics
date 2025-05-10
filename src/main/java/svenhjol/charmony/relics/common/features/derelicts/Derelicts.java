package svenhjol.charmony.relics.common.features.derelicts;

import svenhjol.charmony.core.annotations.FeatureDefinition;
import svenhjol.charmony.core.base.Mod;
import svenhjol.charmony.core.base.SidedFeature;
import svenhjol.charmony.core.enums.Side;

@FeatureDefinition(side = Side.Common)
public final class Derelicts extends SidedFeature {
    public final Registers registers;
    public final Providers providers;

    public Derelicts(Mod mod) {
        super(mod);
        registers = new Registers(this);
        providers = new Providers(this);
    }

    public static Derelicts feature() {
        return Mod.getSidedFeature(Derelicts.class);
    }
}
