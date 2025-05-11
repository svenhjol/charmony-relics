package svenhjol.charmony.relics.common.features.relics;

import svenhjol.charmony.core.annotations.Configurable;
import svenhjol.charmony.core.annotations.FeatureDefinition;
import svenhjol.charmony.core.base.Mod;
import svenhjol.charmony.core.base.SidedFeature;
import svenhjol.charmony.core.enums.Side;

@FeatureDefinition(side = Side.Common)
public final class Relics extends SidedFeature {
    public final Registers registers;
    public final Providers providers;
    public final Handlers handlers;

    @Configurable(
        name = "Maximum additional levels",
        description = """
            Maximum number of additional enchantment levels above the vanilla default to add to the relic."""
    )
    private static int maxAdditionalLevels = 3;

    public Relics(Mod mod) {
        super(mod);
        registers = new Registers(this);
        providers = new Providers(this);
        handlers = new Handlers(this);
    }

    public static Relics feature() {
        return Mod.getSidedFeature(Relics.class);
    }

    public int maxAdditionalLevels() {
        return maxAdditionalLevels;
    }
}
