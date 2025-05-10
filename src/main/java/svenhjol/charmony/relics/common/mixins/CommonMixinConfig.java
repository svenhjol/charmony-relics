package svenhjol.charmony.relics.common.mixins;

import svenhjol.charmony.core.base.MixinConfig;
import svenhjol.charmony.core.enums.Side;
import svenhjol.charmony.relics.RelicsMod;

public class CommonMixinConfig extends MixinConfig {
    @Override
    protected String modId() {
        return RelicsMod.ID;
    }

    @Override
    protected String modRoot() {
        return "svenhjol.charmony.relics";
    }

    @Override
    protected Side side() {
        return Side.Common;
    }
}
