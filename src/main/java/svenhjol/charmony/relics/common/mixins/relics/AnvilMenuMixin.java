package svenhjol.charmony.relics.common.mixins.relics;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import svenhjol.charmony.relics.common.features.relics.Relics;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {
    @Shadow @Final private DataSlot cost;

    public AnvilMenuMixin(@Nullable MenuType<?> menuType, int syncId, Inventory inventory, ContainerLevelAccess access, ItemCombinerMenuSlotDefinition slotDefinition) {
        super(menuType, syncId, inventory, access, slotDefinition);
    }

    @Redirect(
        method = "createResult",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;setEnchantments(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/enchantment/ItemEnchantments;)V"
        )
    )
    private void hookCreateResult(ItemStack outputStack, ItemEnchantments enchantments) {
        if (!Relics.feature().enabled()) {
            // Vanilla behavior
            EnchantmentHelper.setEnchantments(outputStack, enchantments);
            return;
        }

        var inputStack = inputSlots.getItem(1);
        var cost = Relics.feature().handlers.transferRelicEnchantments(inputStack, outputStack, enchantments, this.cost.get());
        this.cost.set(cost);
    }
}
