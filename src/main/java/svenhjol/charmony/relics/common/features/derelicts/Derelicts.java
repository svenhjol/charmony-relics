package svenhjol.charmony.relics.common.features.derelicts;

import net.minecraft.util.Mth;
import svenhjol.charmony.core.annotations.Configurable;
import svenhjol.charmony.core.annotations.FeatureDefinition;
import svenhjol.charmony.core.base.Mod;
import svenhjol.charmony.core.base.SidedFeature;
import svenhjol.charmony.core.enums.Side;

@FeatureDefinition(side = Side.Common, description = """
    Rare structures found at the lowest levels of the world that contain forgotten treasures.""")
public final class Derelicts extends SidedFeature {
    public final Registers registers;
    public final Handlers handlers;
    public final Providers providers;

    @Configurable(
        name = "Ancient City loot chance",
        description = """
            Chance (out of 1.0) of a derelict map being found in an Ancient City loot chest."""
    )
    private static double mapLootChance = 0.2d;

    @Configurable(
        name = "Derelict runestone chance",
        description = """
            Chance (out of 1.0) of a runestone linking to a derelict.
            This only works if the Charmony Runestones mod is present and enabled.
            The runestone will be tested after processing other registered runestones in a series,
            so the likelihood of finding one will appear to be lower than the given value.""",
        requireRestart = false
    )
    private static double runestoneChance = 0.08d;

    public Derelicts(Mod mod) {
        super(mod);
        registers = new Registers(this);
        handlers = new Handlers(this);
        providers = new Providers(this);
    }

    public static Derelicts feature() {
        return Mod.getSidedFeature(Derelicts.class);
    }

    public double runestoneChance() {
        return Mth.clamp(runestoneChance, 0.0d, 1.0d);
    }

    public double mapLootChance() {
        return Mth.clamp(mapLootChance, 0.0d, 1.0d);
    }
}
