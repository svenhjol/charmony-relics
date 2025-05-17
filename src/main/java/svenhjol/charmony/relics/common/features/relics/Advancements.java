package svenhjol.charmony.relics.common.features.relics;

import net.minecraft.world.entity.player.Player;
import svenhjol.charmony.core.base.Setup;
import svenhjol.charmony.core.helpers.AdvancementHelper;

public class Advancements extends Setup<Relics> {
    public Advancements(Relics feature) {
        super(feature);
    }

    public void obtainedRelic(Player player) {
        AdvancementHelper.trigger("obtained_relic", player);
    }
}
