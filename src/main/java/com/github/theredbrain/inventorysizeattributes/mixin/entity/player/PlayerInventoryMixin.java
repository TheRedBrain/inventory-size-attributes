package com.github.theredbrain.inventorysizeattributes.mixin.entity.player;

import com.github.theredbrain.inventorysizeattributes.entity.player.DuckPlayerEntityMixin;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {

	@Shadow
	@Final
	public PlayerEntity player;

	@Shadow public int selectedSlot;

	@WrapOperation(
			method = "getEmptySlot",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/item/ItemStack;isEmpty()Z"
			)
	)
	public boolean inventorysizeattributes$wrap_isEmpty(ItemStack instance, Operation<Boolean> original, @Local int i) {
		return original.call(instance) && inventorysizeattributes$isIndexInsideActiveInventorySize(i);
	}

	@WrapOperation(
			method = "getOccupiedSlotWithRoomForStack",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/entity/player/PlayerInventory;canStackAddMore(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z",
					ordinal = 2
			)
	)
	private boolean inventorysizeattributes$wrap_canStackAddMore(PlayerInventory instance, ItemStack existingStack, ItemStack stack, Operation<Boolean> original, @Local int i) {
		return original.call(instance, existingStack, stack) && inventorysizeattributes$isIndexInsideActiveInventorySize(i);
	}

	/**
	 * @author TheRedBrain
	 * @reason respect active hotbar size
	 */
	@Overwrite
	public void scrollInHotbar(double scrollAmount) {
		int i = (int)Math.signum(scrollAmount);
		int hotbarSize = ((DuckPlayerEntityMixin) this.player).inventorysizeattributes$getActiveHotbarSlotAmount();
		this.selectedSlot -= i;

		while (this.selectedSlot < 0) {
			this.selectedSlot += hotbarSize;
		}

		while (this.selectedSlot >= hotbarSize) {
			this.selectedSlot -= hotbarSize;
		}
	}

	@Unique
	private boolean inventorysizeattributes$isIndexInsideActiveInventorySize(int index) {
		return (index < 9 && index < ((DuckPlayerEntityMixin) this.player).inventorysizeattributes$getActiveHotbarSlotAmount()) || (index >= 9 && (index - 9) < ((DuckPlayerEntityMixin) this.player).inventorysizeattributes$getActiveInventorySlotAmount());
	}
}
