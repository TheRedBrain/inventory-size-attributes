package com.github.theredbrain.inventorysizeattributes.mixin.screen;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ScreenHandler.class)
public abstract class ScreenHandlerMixin {

	@Shadow
	@Final
	public DefaultedList<Slot> slots;

	@WrapOperation(
			method = "insertItem",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/item/ItemStack;areItemsAndComponentsEqual(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z"
			)
	)
	public boolean inventorysizeattributes$wrap_areItemsAndComponentsEqual(ItemStack stack, ItemStack otherStack, Operation<Boolean> original, @Local Slot slot) {
		return original.call(stack, otherStack) && slot.isEnabled();
	}

	@WrapOperation(
			method = "insertItem",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/screen/slot/Slot;canInsert(Lnet/minecraft/item/ItemStack;)Z"
			)
	)
	public boolean inventorysizeattributes$wrap_canInsert(Slot instance, ItemStack stack, Operation<Boolean> original, @Local Slot slot) {
		return original.call(instance, stack) && slot.isEnabled();
	}
}
