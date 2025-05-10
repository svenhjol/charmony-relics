package svenhjol.charmony.relics.client;

import net.fabricmc.api.ClientModInitializer;
import svenhjol.charmony.core.enums.Side;
import svenhjol.charmony.relics.RelicsMod;

public final class ClientInitializer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Ensure charmony is launched first.
        svenhjol.charmony.core.client.ClientInitializer.init();

        // Prepare and run the mod.
        var mod = RelicsMod.instance();
        mod.run(Side.Client);
    }
}
