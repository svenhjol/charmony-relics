
package svenhjol.charmony.relics.common.features.relics.loot_functions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import svenhjol.charmony.api.relics.RelicType;
import svenhjol.charmony.api.relics.RelicsApi;
import svenhjol.charmony.relics.common.features.relics.Constants;
import svenhjol.charmony.relics.common.features.relics.Relics;

import java.util.List;

public class WeaponLootFunction extends LootItemConditionalFunction {
    public static final MapCodec<WeaponLootFunction> CODEC = RecordCodecBuilder.mapCodec(
        instance -> WeaponLootFunction.commonFields(instance)
            .apply(instance, WeaponLootFunction::new));

    public WeaponLootFunction(List<LootItemCondition> list) {
        super(list);
    }

    @Override
    public LootItemFunctionType<? extends LootItemConditionalFunction> getType() {
        return Relics.feature().registers.lootFunctions.get(Constants.WEAPON_LOOT).get();
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        return RelicsApi.instance().randomRelicOfType(
            context.getResolver(), context.getRandom(), RelicType.Weapon);
    }
}
