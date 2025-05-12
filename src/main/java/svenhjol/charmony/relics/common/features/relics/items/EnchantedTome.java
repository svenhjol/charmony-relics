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

/**
 * An enchanted book that holds levels above the maximum enchantment levels.
 * If the glint colors mod is present then they also have a random enchantment color.
 * Use an anvil to apply these enchantments (and the glint color) to an item.
 */
public class EnchantedTome implements RelicDefinition {
    @Override
    public String id() {
        return "enchanted_tome";
    }

    @Override
    public MutableComponent name() {
        return Component.translatable("item.charmony-relics.enchanted_tome");
    }

    @Override
    public ItemStack item() {
        return new ItemStack(Items.ENCHANTED_BOOK);
    }

    @Override
    public Rarity rarity() {
        return Rarity.RARE;
    }

    @Override
    public RelicType type() {
        return RelicType.Book;
    }

    @Override
    public int numberOfEnchantments(RandomSource random) {
        return random.nextInt(2) + 1;
    }

    @Override
    public int additionalLevels(RandomSource random) {
        return Math.min(Relics.feature().maxAdditionalLevels(), random.nextInt(5) + 1);
    }

    @Override
    public List<ResourceKey<Enchantment>> validEnchantments(HolderGetter.Provider provider) {
        return TagHelper.getResourceKeys(provider.lookupOrThrow(Registries.ENCHANTMENT), Tags.ON_ENCHANTED_TOMES);
    }
}
