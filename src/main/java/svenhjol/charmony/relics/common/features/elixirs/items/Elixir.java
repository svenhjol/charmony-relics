package svenhjol.charmony.relics.common.features.elixirs.items;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import svenhjol.charmony.api.elixirs.ElixirDefinition;
import svenhjol.charmony.relics.common.features.relics.Helpers;

import java.util.List;

public class Elixir implements ElixirDefinition {
    @Override
    public String id() {
        return "elixir";
    }

    @Override
    public MutableComponent name(RandomSource random) {
        return Helpers.prefixedPotionName(random);
    }

    @Override
    public int numberOfEffects(RandomSource random) {
        return random.nextDouble() < 0.15d ? 2 : 1;
    }

    @Override
    public List<Holder<MobEffect>> validEffects(HolderGetter.Provider provider) {
        return List.of(
            MobEffects.SPEED,
            MobEffects.HASTE,
            MobEffects.STRENGTH,
            MobEffects.JUMP_BOOST,
            MobEffects.RESISTANCE,
            MobEffects.HEALTH_BOOST,
            MobEffects.ABSORPTION,
            MobEffects.SATURATION,
            MobEffects.LUCK,
            MobEffects.REGENERATION,
            MobEffects.NIGHT_VISION,
            MobEffects.INVISIBILITY,
            MobEffects.WATER_BREATHING,
            MobEffects.FIRE_RESISTANCE
        );
    }
}
