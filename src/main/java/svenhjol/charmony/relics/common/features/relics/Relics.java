package svenhjol.charmony.relics.common.features.relics;

import svenhjol.charmony.core.annotations.Configurable;
import svenhjol.charmony.core.annotations.FeatureDefinition;
import svenhjol.charmony.core.base.Mod;
import svenhjol.charmony.core.base.SidedFeature;
import svenhjol.charmony.core.enums.Side;

@FeatureDefinition(side = Side.Common, description = """
    Adds books and items enchanted with levels higher than the enchantment maximum
    or enchantments that can not normally be combined.""")
public final class Relics extends SidedFeature {
    public final Registers registers;
    public final Providers providers;
    public final Handlers handlers;
    public final Advancements advancements;

    @Configurable(
        name = "Maximum additional levels",
        description = """
            Maximum number of additional enchantment levels above the vanilla default to add to the relic.""",
        requireRestart = false
    )
    private static int maxAdditionalLevels = 3;

    @Configurable(
        name = "Anvil cost per additional level",
        description = """
            The cost (in XP) of each level above the enchantment's maximum when applying on the anvil.""",
        requireRestart = false
    )
    private static int anvilCostPerLevel = 3;

    @Configurable(
        name = "Maximum anvil cost",
        description = """
            Maximum cost when using an anvil to apply an enchanted book with levels above the default.
            In the vanilla game, items cannot be given a cost greater than 39.
            Some mods (including Charmony Tweaks) remove this upper limit, so if you are using such a mod
            you can set the value to zero to ignore the maxmimum cost.""",
        requireRestart = false
    )
    private static int maxAnvilCost = 39;

    public Relics(Mod mod) {
        super(mod);
        registers = new Registers(this);
        providers = new Providers(this);
        handlers = new Handlers(this);
        advancements = new Advancements(this);
    }

    public static Relics feature() {
        return Mod.getSidedFeature(Relics.class);
    }

    public int maxAdditionalLevels() {
        return maxAdditionalLevels;
    }

    public int maxAnvilCost() {
        return maxAnvilCost;
    }

    public int anvilCostPerLevel() {
        return anvilCostPerLevel;
    }
}
