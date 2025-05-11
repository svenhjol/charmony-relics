package svenhjol.charmony.relics.common.features.relics.items;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import svenhjol.charmony.api.relics.RelicDefinition;
import svenhjol.charmony.api.relics.RelicType;

import java.util.List;

public class EnchantedBook implements RelicDefinition {
    @Override
    public String id() {
        return "enchanted_book";
    }

    @Override
    public MutableComponent name() {
        return Component.translatable("item.charmony-relics.enchanted_book");
    }

    @Override
    public ItemStack item() {
        return new ItemStack(Items.ENCHANTED_BOOK);
    }

    @Override
    public RelicType type() {
        return RelicType.Book;
    }

    @Override
    public List<ResourceKey<Enchantment>> validEnchantments() {
        return List.of(
            Enchantments.FLAME,
            Enchantments.FORTUNE,
            Enchantments.LURE,
            Enchantments.SMITE,
            Enchantments.BANE_OF_ARTHROPODS,
            Enchantments.BLAST_PROTECTION,
            Enchantments.EFFICIENCY,
            Enchantments.UNBREAKING,
            Enchantments.FIRE_ASPECT,
            Enchantments.POWER,
            Enchantments.SOUL_SPEED,
            Enchantments.LUCK_OF_THE_SEA,
            Enchantments.LOOTING,
            Enchantments.THORNS
        );
    }
}
