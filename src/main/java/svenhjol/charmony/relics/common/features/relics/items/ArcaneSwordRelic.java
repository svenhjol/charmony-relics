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
 * A sword that allows the illegal combination of sharpness, smite and bane of arthropods.
 */
public class ArcaneSwordRelic implements RelicDefinition {
    @Override
    public String id() {
        return "arcane_sword";
    }

    @Override
    public MutableComponent name(RandomSource random) {
        return Component.translatable("item.charmony.arcane_sword");
    }

    @Override
    public ItemStack item(RandomSource random) {
        return new ItemStack(Items.DIAMOND_SWORD);
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
        return DyeColor.LIGHT_BLUE;
    }

    @Override
    public int numberOfEnchantments(RandomSource random) {
        return 3;
    }

    @Override
    public Map<ResourceKey<Enchantment>, Integer> fixedEnchantments() {
        return Map.of(
            Enchantments.SHARPNESS, 5,
            Enchantments.SMITE, 5,
            Enchantments.BANE_OF_ARTHROPODS, 5
        );
    }
}
