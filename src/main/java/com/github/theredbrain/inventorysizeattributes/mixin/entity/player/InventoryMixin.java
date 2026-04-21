package com.github.theredbrain.inventorysizeattributes.mixin.entity.player;

import com.github.theredbrain.inventorysizeattributes.entity.player.DuckPlayerEntityMixin;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Inventory.class)
public abstract class InventoryMixin {

	@Shadow
	@Final
	public Player player;

	@WrapOperation(
			method = "getFreeSlot",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z"
			)
	)
	public boolean inventorysizeattributes$wrap_isEmpty(ItemStack instance, Operation<Boolean> original, @Local(name = "i") int i) {
		return original.call(instance) && inventorysizeattributes$isIndexInsideActiveInventorySize(i);
	}

	@WrapOperation(
			method = "getSlotWithRemainingSpace",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/entity/player/Inventory;hasRemainingSpaceForItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z",
					ordinal = 2
			)
	)
	private boolean inventorysizeattributes$wrap_canStackAddMore(Inventory instance, ItemStack slotItemStack, ItemStack newItemStack, Operation<Boolean> original, @Local(name = "i") int i) {
		return original.call(instance, slotItemStack, newItemStack) && inventorysizeattributes$isIndexInsideActiveInventorySize(i);
	}

	@Unique
	private boolean inventorysizeattributes$isIndexInsideActiveInventorySize(int index) {
		return (index < 9 && index < ((DuckPlayerEntityMixin) this.player).inventorysizeattributes$getActiveHotbarSlotAmount()) || (index >= 9 && (index - 9) < ((DuckPlayerEntityMixin) this.player).inventorysizeattributes$getActiveInventorySlotAmount());
	}
}
