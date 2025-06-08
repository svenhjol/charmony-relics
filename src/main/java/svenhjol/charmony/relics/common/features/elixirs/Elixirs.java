package svenhjol.charmony.relics.common.features.elixirs;

import net.minecraft.util.Mth;
import svenhjol.charmony.api.core.Configurable;
import svenhjol.charmony.api.core.FeatureDefinition;
import svenhjol.charmony.api.core.Side;
import svenhjol.charmony.core.base.Mod;
import svenhjol.charmony.core.base.SidedFeature;

@FeatureDefinition(side = Side.Common, description = "TODO")
@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
public final class Elixirs extends SidedFeature {
    public final Registers registers;
    public final Handlers handlers;
    public final Providers providers;

    @Configurable(
        name = "Dungeon chest chance",
        description = """
            Chance (out of 1.0) of an elixir being found in a dungeon chest."""
    )
    private static double dungeonChestChance = 0.12d;

    @Configurable(
        name = "Stronghold corridor chest chance",
        description = """
            Chance (out of 1.0) of an elixir being found in a stronghold corridor chest."""
    )
    private static double strongholdCorridorChance = 0.25d;

    @Configurable(
        name = "Cleric gift chance",
        description = """
            Chance (out of 1.0) of an elixir being given by a cleric when the player has Hero of the Village."""
    )
    private static double clericGiftChance = 0.12d;

    @Configurable(
        name = "Trial Chambers vault chance",
        description = """
            Chance (out of 1.0) of an elixir being provided by a Trial Chambers vault."""
    )
    private static double trialChambersChance = 0.25d;

    public Elixirs(Mod mod) {
        super(mod);
        handlers = new Handlers(this);
        providers = new Providers(this);
        registers = new Registers(this);
    }

    public static Elixirs feature() {
        return Mod.getSidedFeature(Elixirs.class);
    }

    public double dungeonChestChance() {
        return Mth.clamp(dungeonChestChance, 0.0d, 1.0d);
    }

    public double strongholdCorridorChance() {
        return Mth.clamp(strongholdCorridorChance, 0.0d, 1.0d);
    }

    public double clericGiftChance() {
        return Mth.clamp(clericGiftChance, 0.0d, 1.0d);
    }

    public double trialChambersChance() {
        return Mth.clamp(trialChambersChance, 0.0d, 1.0d);
    }
}
