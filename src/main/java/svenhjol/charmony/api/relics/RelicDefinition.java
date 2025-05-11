package svenhjol.charmony.api.relics;

import net.minecraft.Util;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import svenhjol.charmony.api.glint_colors.GlintColorsApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface RelicDefinition extends StringRepresentable {
    String RELIC_TAG = "charmony-relic";

    /**
     * Unique id for indexing and lookups.
     */
    String id();

    /**
     * Translatable name for this relic.
     */
    MutableComponent name();

    /**
     * Base itemstack for this relic.
     */
    ItemStack item();

    /**
     * Relic type. Can be used to sort relics into specific loot tables for example.
     */
    RelicType type();

    /**
     * Additional levels above the enchantment maximum.
     */
    default int additionalLevels(RandomSource random) {
        return 1;
    }

    /**
     * Number of enchantments to include from the fixed or valid list.
     */
    default int numberOfEnchantments(RandomSource random) {
        return 1;
    }

    default DyeColor glintColor(RandomSource random) {
        var colors = List.of(DyeColor.values());
        return colors.get(random.nextInt(colors.size()));
    }

    /**
     * A list of enchantments to apply to the item.
     * The max level will be calculated for each enchantment.
     */
    default List<ResourceKey<Enchantment>> validEnchantments() {
        return List.of();
    }

    /**
     * A map of enchantments and levels to apply directly to the item without modification.
     */
    default Map<ResourceKey<Enchantment>, Integer> fixedEnchantments() {
        return Map.of();
    }

    /**
     * Output the relic for this definition.
     */
    default ItemStack relicItem(HolderGetter.Provider provider, RandomSource random) {
        var registry = provider.lookupOrThrow(Registries.ENCHANTMENT);

        // Get the base itemstack of the relic.
        var item = item();

        // Set the translated custom name of the relic.
        item.set(DataComponents.CUSTOM_NAME, name());

        // Get fixed/valid enchantments and apply them to the relic.
        var itemEnchantments = new ItemEnchantments.Mutable(EnchantmentHelper.getEnchantmentsForCrafting(item));

        var fixed = fixedEnchantments();
        if (!fixed.isEmpty()) {
            var max = Math.min(numberOfEnchantments(random), fixed.size());
            var keys = new ArrayList<>(fixed.keySet());
            Util.shuffle(keys, random);

            for (var i = 0; i < max; i++) {
                var key = keys.get(i);
                var holder = registry.getOrThrow(key);
                itemEnchantments.set(holder, fixed.get(key));
            }
        }

        var valid = new ArrayList<>(validEnchantments());
        if (!valid.isEmpty()) {
            Util.shuffle(valid, random);
            var max = numberOfEnchantments(random);
            var keys = new ArrayList<>(valid);
            Util.shuffle(keys, random);

            for (var i = 0; i < max; i++) {
                var key = keys.get(i);
                var holder = registry.getOrThrow(key);
                var additional = additionalLevels(random);
                var maxLevel = holder.value().getMaxLevel();
                var newMaxLevel = maxLevel + additional;
                itemEnchantments.set(holder, newMaxLevel);
            }
        }

        // Set the enchantments back onto the item.
        // We use minecraft's API to do this because it handles both items and books.
        EnchantmentHelper.setEnchantments(item, itemEnchantments.toImmutable());

        // Apply enchantment glint color to the item, if applicable.
        var color = glintColor(random);
        GlintColorsApi.instance().apply(item, color);

        // Apply the "relic" tag to this item.
        var tag = new CompoundTag();
        tag.putBoolean(RELIC_TAG, true);
        item.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));

        return item;
    }

    @Override
    default String getSerializedName() {
        return id();
    }
}
