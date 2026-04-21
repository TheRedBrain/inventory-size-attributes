package com.github.theredbrain.inventorysizeattributes.mixin.screen;

import com.github.theredbrain.inventorysizeattributes.screen.DuckMenuMixin;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractContainerMenu.class)
public abstract class AbstractContainerMenuMixin implements DuckMenuMixin {

	@Shadow
	@Final
	public NonNullList<Slot> slots;

	@WrapOperation(
			method = "moveItemStackTo",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/item/ItemStack;isSameItemSameComponents(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z"
			)
	)
	public boolean inventorysizeattributes$wrap_isSameItemSameComponents(ItemStack stack, ItemStack otherStack, Operation<Boolean> original, @Local Slot slot) {
		return original.call(stack, otherStack) && slot.isActive();
	}

	@WrapOperation(
			method = "moveItemStackTo",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/inventory/Slot;mayPlace(Lnet/minecraft/world/item/ItemStack;)Z"
			)
	)
	public boolean inventorysizeattributes$wrap_mayPlace(Slot instance, ItemStack stack, Operation<Boolean> original, @Local Slot slot) {
		return original.call(instance, stack) && slot.isActive();
	}
}
