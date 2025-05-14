package svenhjol.charmony.relics.common.features.derelicts.loot_functions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import svenhjol.charmony.api.relics.RelicsApi;
import svenhjol.charmony.relics.common.features.derelicts.Constants;
import svenhjol.charmony.relics.common.features.derelicts.Derelicts;

import java.util.List;

public class RelicLootFunction extends LootItemConditionalFunction {
    public static final MapCodec<RelicLootFunction> CODEC = RecordCodecBuilder.mapCodec(
        instance -> RelicLootFunction.commonFields(instance)
            .apply(instance, RelicLootFunction::new));

    public RelicLootFunction(List<LootItemCondition> list) {
        super(list);
    }

    @Override
    public LootItemFunctionType<? extends LootItemConditionalFunction> getType() {
        return Derelicts.feature().registers.lootFunctions.get(Constants.RELIC_LOOT).get();
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        return RelicsApi.instance().randomRelic(context.getResolver(), context.getRandom());
    }
}
