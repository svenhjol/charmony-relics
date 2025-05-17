package svenhjol.charmony.relics.common.features.relics;

import net.minecraft.Util;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import svenhjol.charmony.api.glint_colors.GlintColorsApi;
import svenhjol.charmony.api.relics.RelicDefinition;
import svenhjol.charmony.core.base.Setup;

import java.util.ArrayList;

public class Handlers extends Setup<Relics> {
    public Handlers(Relics feature) {
        super(feature);
    }

    public int transferRelicEnchantments(ItemStack input, ItemStack output, ItemEnchantments enchantments, int cost) {
        if (input.isEmpty() || output.isEmpty()) return cost;

        var newEnchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
        var inputEnchantments = EnchantmentHelper.getEnchantmentsForCrafting(input);

        for (var holder : inputEnchantments.keySet()) {
            var level = inputEnchantments.getLevel(holder);
            if (level > holder.value().getMaxLevel() && holder.value().canEnchant(output)) {
                cost += (level * feature().anvilCostPerLevel());
                newEnchantments.set(holder, level);
            }
        }

        for (var holder : enchantments.keySet()) {
            var level = enchantments.getLevel(holder);
            if (!newEnchantments.keySet().contains(holder) && holder.value().canEnchant(output)) {
                newEnchantments.set(holder, level);
            }
        }

        GlintColorsApi.instance().getColor(input).ifPresent(
            color -> GlintColorsApi.instance().apply(output, color));

        EnchantmentHelper.setEnchantments(output, newEnchantments.toImmutable());

        if (feature().maxAnvilCost() > 0) {
            return Math.min(feature().maxAnvilCost(), cost);
        }

        return cost;
    }

    public ItemStack createRelicItem(RelicDefinition definition, HolderGetter.Provider provider, RandomSource random) {
        var registry = provider.lookupOrThrow(Registries.ENCHANTMENT);

        // Get the base itemstack of the relic.
        var item = definition.item(random);

        var numEnchantments = definition.numberOfEnchantments(random);

        // Set the translated custom name of the relic.
        item.set(DataComponents.CUSTOM_NAME, definition.name(random));

        // Get fixed/valid enchantments and apply them to the relic.
        var itemEnchantments = new ItemEnchantments.Mutable(EnchantmentHelper.getEnchantmentsForCrafting(item));

        var fixed = definition.fixedEnchantments();
        if (!fixed.isEmpty()) {
            var max = Math.min(numEnchantments, fixed.size());
            var keys = new ArrayList<>(fixed.keySet());
            Util.shuffle(keys, random);

            for (var i = 0; i < max; i++) {
                var key = keys.get(i);
                var holder = registry.getOrThrow(key);
                itemEnchantments.set(holder, fixed.get(key));
            }
        }

        var valid = new ArrayList<>(definition.validEnchantments(provider));
        if (!valid.isEmpty()) {
            Util.shuffle(valid, random);
            var keys = new ArrayList<>(valid);
            Util.shuffle(keys, random);

            for (var i = 0; i < numEnchantments; i++) {
                var key = keys.get(i);
                var holder = registry.getOrThrow(key);
                var additional = definition.additionalLevels(random);
                var maxLevel = holder.value().getMaxLevel();
                var newMaxLevel = maxLevel + additional;
                itemEnchantments.set(holder, definition.upperBound(key, newMaxLevel));
            }
        }

        // Set the enchantments back onto the item.
        // We use minecraft's API to do this because it handles both items and books.
        EnchantmentHelper.setEnchantments(item, itemEnchantments.toImmutable());

        // Apply enchantment glint type to the item, if applicable.
        var color = definition.glintColor(random);
        GlintColorsApi.instance().apply(item, color);

        // Apply rarity to the item.
        var rarity = definition.rarity();
        item.set(DataComponents.RARITY, rarity);

        // Apply lore to the item, if applicable.
        definition.lore().ifPresent(lore -> item.set(DataComponents.LORE, lore));

        // Apply relic data.
        var data = new RelicData(definition.type());
        RelicData.set(item, data);

        return item;
    }

    public boolean hasRelicInInventory(Player player) {
        var inventory = player.getInventory();
        for (var item : inventory) {
            if (RelicData.has(item)) {
                return true;
            }
        }
        return false;
    }

    public void playerTick(Player player) {
        var level = player.level();
        if (level instanceof ServerLevel serverLevel
            && serverLevel.getGameTime() % 20 == 0
            && hasRelicInInventory(player)) {
            feature().advancements.obtainedRelic(player);
        }
    }
}
