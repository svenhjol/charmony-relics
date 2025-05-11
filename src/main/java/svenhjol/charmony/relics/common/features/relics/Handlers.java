package svenhjol.charmony.relics.common.features.relics;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import svenhjol.charmony.api.glint_colors.GlintColorsApi;
import svenhjol.charmony.core.base.Setup;

public class Handlers extends Setup<Relics> {
    public Handlers(Relics feature) {
        super(feature);
    }

    public int transferRelicEnchantments(ItemStack input, ItemStack output, ItemEnchantments enchantments) {
        int extraCost = 0;
        if (input.isEmpty() || output.isEmpty()) return 0;

        var newEnchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
        var inputEnchantments = EnchantmentHelper.getEnchantmentsForCrafting(input);

        for (var holder : inputEnchantments.keySet()) {
            var level = inputEnchantments.getLevel(holder);
            if (level > holder.value().getMaxLevel() && holder.value().canEnchant(output)) {
                extraCost += level;
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
        return extraCost;
    }
}
