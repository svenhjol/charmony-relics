package svenhjol.charmony.relics.common.features.relics.items;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import svenhjol.charmony.api.relics.RelicDefinition;
import svenhjol.charmony.api.relics.RelicType;

import java.util.Map;

/**
 * A bow that allows the illegal combination of infinity and mending.
 */
public class ArcaneBowRelic implements RelicDefinition {
    @Override
    public String id() {
        return "arcane_bow";
    }

    @Override
    public MutableComponent name(RandomSource random) {
        return Component.translatable("item.charmony-relics.arcane_bow");
    }

    @Override
    public ItemStack item(RandomSource random) {
        return new ItemStack(Items.BOW);
    }

    @Override
    public Rarity rarity() {
        return Rarity.EPIC;
    }

    @Override
    public RelicType type() {
        return RelicType.Weapon;
    }

    @Override
    public DyeColor glintColor(RandomSource random) {
        return DyeColor.LIGHT_GRAY;
    }

    @Override
    public int numberOfEnchantments(RandomSource random) {
        return 2;
    }

    @Override
    public Map<ResourceKey<Enchantment>, Integer> fixedEnchantments() {
        return Map.of(
            Enchantments.INFINITY, 1,
            Enchantments.MENDING, 1
        );
    }
}
