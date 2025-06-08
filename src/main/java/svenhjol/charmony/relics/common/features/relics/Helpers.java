package svenhjol.charmony.relics.common.features.relics;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.RandomSource;

import java.util.Arrays;

public final class Helpers {
    public static MutableComponent prefixedItemName(MutableComponent itemName, RandomSource random) {
        var prefix = randomPrefix(random);
        return Component.translatable("gui.charmony.prefixed_item_name", prefix, itemName);
    }

    public static MutableComponent prefixedPotionName(RandomSource random) {
        var prefix = randomPrefix(random);
        var potionName = randomPotionName(random);
        return Component.translatable("gui.charmony.prefixed_item_name", prefix, potionName);
    }

    public static String randomPrefix(RandomSource random) {
        var prefixes = Arrays.stream(Component.translatable("gui.charmony.prefixes").getString().split(",")).toList();
        return prefixes.get(random.nextInt(prefixes.size()));
    }

    public static String randomPotionName(RandomSource random) {
        var potions = Arrays.stream(Component.translatable("gui.charmony.potions").getString().split(",")).toList();
        return potions.get(random.nextInt(potions.size()));
    }
}
