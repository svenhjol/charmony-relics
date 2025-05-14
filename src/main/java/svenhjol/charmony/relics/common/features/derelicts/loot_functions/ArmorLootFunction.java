
package svenhjol.charmony.relics.common.features.derelicts.loot_functions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import svenhjol.charmony.api.relics.RelicType;
import svenhjol.charmony.api.relics.RelicsApi;
import svenhjol.charmony.relics.common.features.derelicts.Constants;
import svenhjol.charmony.relics.common.features.derelicts.Derelicts;

import java.util.List;

public class ArmorLootFunction extends LootItemConditionalFunction {
    public static final MapCodec<ArmorLootFunction> CODEC = RecordCodecBuilder.mapCodec(
        instance -> ArmorLootFunction.commonFields(instance)
            .apply(instance, ArmorLootFunction::new));

    public ArmorLootFunction(List<LootItemCondition> list) {
        super(list);
    }

    @Override
    public LootItemFunctionType<? extends LootItemConditionalFunction> getType() {
        return Derelicts.feature().registers.lootFunctions.get(Constants.ARMOR_LOOT).get();
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        return RelicsApi.instance().randomRelicOfType(
            context.getResolver(), context.getRandom(), RelicType.Armor);
    }
}
