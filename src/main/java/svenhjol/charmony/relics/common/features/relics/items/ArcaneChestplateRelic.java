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
 * A chestplate that allows the illegal combination of normal, blast, projectile and fire protection.
 */
public class ArcaneChestplateRelic implements RelicDefinition {
    @Override
    public String id() {
        return "arcane_chestplate";
    }

    @Override
    public MutableComponent name(RandomSource random) {
        return Component.translatable("item.charmony.arcane_chestplate");
    }

    @Override
    public ItemStack item(RandomSource random) {
        return new ItemStack(Items.DIAMOND_CHESTPLATE);
    }

    @Override
    public Rarity rarity() {
        return Rarity.EPIC;
    }

    @Override
    public RelicType type() {
        return RelicType.Armor;
    }

    @Override
    public DyeColor glintColor(RandomSource random) {
        return DyeColor.LIGHT_GRAY;
    }

    @Override
    public int numberOfEnchantments(RandomSource random) {
        return 4;
    }

    @Override
    public Map<ResourceKey<Enchantment>, Integer> fixedEnchantments() {
        return Map.of(
            Enchantments.PROTECTION, 4,
            Enchantments.BLAST_PROTECTION, 4,
            Enchantments.PROJECTILE_PROTECTION, 4,
            Enchantments.FIRE_PROTECTION, 4
        );
    }
}
