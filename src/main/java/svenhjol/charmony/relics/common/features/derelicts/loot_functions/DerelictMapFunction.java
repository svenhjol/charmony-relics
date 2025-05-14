package svenhjol.charmony.relics.common.features.derelicts.loot_functions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import svenhjol.charmony.core.helpers.WorldHelper;
import svenhjol.charmony.relics.common.features.derelicts.Constants;
import svenhjol.charmony.relics.common.features.derelicts.Derelicts;

import java.util.List;

public class DerelictMapFunction extends LootItemConditionalFunction {
    public static final MapCodec<DerelictMapFunction> CODEC = RecordCodecBuilder.mapCodec(
        instance -> DerelictMapFunction.commonFields(instance)
            .apply(instance, DerelictMapFunction::new));

    public DerelictMapFunction(List<LootItemCondition> list) {
        super(list);
    }

    @Override
    public LootItemFunctionType<? extends LootItemConditionalFunction> getType() {
        return Derelicts.feature().registers.lootFunctions.get(Constants.MAP_LOOT).get();
    }

    @Override
    protected ItemStack run(ItemStack itemStack, LootContext lootContext) {
        var level = lootContext.getLevel();
        var origin = lootContext.getOptionalParameter(LootContextParams.ORIGIN);
        if (origin == null) return ItemStack.EMPTY;

        var pos = WorldHelper.addRandomOffset(level, BlockPos.containing(origin), lootContext.getRandom(), 2000, 8000);
        return Derelicts.feature().handlers.createMap(level, pos).orElse(ItemStack.EMPTY);
    }
}
