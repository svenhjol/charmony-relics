package svenhjol.charmony.relics.common.features.relics;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;

public class RelicLootFunction extends LootItemConditionalFunction {
    public static final MapCodec<RelicLootFunction> CODEC = RecordCodecBuilder.mapCodec(
        instance -> RelicLootFunction.commonFields(instance)
            .and(ItemStack.CODEC.fieldOf("relic").forGetter(x -> x.relic))
            .apply(instance, RelicLootFunction::new));

    private final ItemStack relic;

    public RelicLootFunction(List<LootItemCondition> list, ItemStack relic) {
        super(list);
        this.relic = relic;
    }

    @Override
    public LootItemFunctionType<? extends LootItemConditionalFunction> getType() {
        return Relics.feature().registers.relicLootFunction.get();
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        return relic;
    }
}
