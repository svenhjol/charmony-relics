package svenhjol.charmony.relics.common;

import net.fabricmc.api.ModInitializer;
import svenhjol.charmony.api.core.Side;
import svenhjol.charmony.relics.RelicsMod;
import svenhjol.charmony.relics.common.features.derelicts.Derelicts;
import svenhjol.charmony.relics.common.features.relics.Relics;

public final class CommonInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        // Ensure charmony is launched first.
        svenhjol.charmony.core.common.CommonInitializer.init();

        // Prepare and run the mod.
        var mod = RelicsMod.instance();
        mod.addSidedFeature(Derelicts.class);
        mod.addSidedFeature(Relics.class);
        mod.run(Side.Common);
    }
}
