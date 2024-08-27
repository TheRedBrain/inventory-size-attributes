package com.github.theredbrain.inventorysizeattributes.mixin.entity;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "createLivingAttributes", at = @At("RETURN"))
    private static void staminaattributes$createLivingAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.getReturnValue()
                .add(InventorySizeAttributes.HOTBAR_SLOT_AMOUNT)
                .add(InventorySizeAttributes.INVENTORY_SLOT_AMOUNT)
        ;
    }
}
