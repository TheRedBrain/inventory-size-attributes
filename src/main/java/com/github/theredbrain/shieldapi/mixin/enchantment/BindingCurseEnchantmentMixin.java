package com.github.theredbrain.shieldapi.mixin.enchantment;

import com.github.theredbrain.shieldapi.item.CustomShieldItem;
import net.minecraft.enchantment.BindingCurseEnchantment;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BindingCurseEnchantment.class)
public class BindingCurseEnchantmentMixin {

	@Inject(method = "isAcceptableItem", at = @At("RETURN"), cancellable = true)
	public void isAcceptableItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(cir.getReturnValue() && !(stack.getItem() instanceof CustomShieldItem));
	}
}
