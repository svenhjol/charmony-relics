package svenhjol.charmony.relics.integration;

import svenhjol.charmony.core.base.Mod;
import svenhjol.charmony.core.integration.BaseModMenuPlugin;
import svenhjol.charmony.relics.RelicsMod;

public class ModMenuPlugin extends BaseModMenuPlugin {
    @Override
    public Mod mod() {
        return RelicsMod.instance();
    }
}
