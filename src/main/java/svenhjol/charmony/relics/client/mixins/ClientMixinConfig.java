package svenhjol.charmony.relics.client.mixins;

import svenhjol.charmony.core.base.MixinConfig;
import svenhjol.charmony.api.core.Side;
import svenhjol.charmony.relics.RelicsMod;

public class ClientMixinConfig extends MixinConfig {
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
        return Side.Client;
    }
}
