package svenhjol.charmony.relics.common.features.relics.items;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.Enchantment;
import svenhjol.charmony.api.relics.RelicDefinition;
import svenhjol.charmony.api.relics.RelicType;
import svenhjol.charmony.core.helpers.TagHelper;
import svenhjol.charmony.relics.common.features.relics.Relics;
import svenhjol.charmony.relics.common.features.relics.Tags;

import java.util.List;

public class BowRelic implements RelicDefinition {
    @Override
    public String id() {
        return "bow";
    }

    @Override
    public MutableComponent name(RandomSource random) {
        return Relics.feature().handlers.prefixedRelicName(Component.translatable("item.minecraft.bow"), random);
    }

    @Override
    public ItemStack item(RandomSource random) {
        return new ItemStack(Items.BOW);
    }

    @Override
    public Rarity rarity() {
        return Rarity.RARE;
    }

    @Override
    public RelicType type() {
        return RelicType.Weapon;
    }

    @Override
    public int numberOfEnchantments(RandomSource random) {
        return random.nextDouble() < 0.1d ? 2 : 1;
    }

    @Override
    public List<ResourceKey<Enchantment>> validEnchantments(HolderGetter.Provider provider) {
        return TagHelper.getResourceKeys(provider.lookupOrThrow(Registries.ENCHANTMENT), Tags.ON_BOW);
    }
}
