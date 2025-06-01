package svenhjol.charmony.relics.common.features.relics;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.RandomSource;

import java.util.Arrays;

public final class Helpers {
    public static MutableComponent prefixedItemName(MutableComponent itemName, RandomSource random) {
        var prefixes = Arrays.stream(Component.translatable("gui.charmony.prefixes").getString().split(",")).toList();
        var prefix = prefixes.get(random.nextInt(prefixes.size()));
        return Component.translatable("gui.charmony.prefixed_item_name", prefix, itemName);
    }
}
