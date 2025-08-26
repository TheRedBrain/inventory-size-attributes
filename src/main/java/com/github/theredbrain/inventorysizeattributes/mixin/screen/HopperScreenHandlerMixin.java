package com.github.theredbrain.inventorysizeattributes.mixin.screen;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributes;
import com.github.theredbrain.inventorysizeattributes.screen.DuckScreenHandlerMixin;
import com.github.theredbrain.slotcustomizationapi.api.SlotCustomization;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.HopperScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HopperScreenHandler.class)
public abstract class HopperScreenHandlerMixin extends ScreenHandler implements DuckScreenHandlerMixin {
	public HopperScreenHandlerMixin(ScreenHandlerType<?> screenHandlerType, int i) {
		super(screenHandlerType, i);
	}

	@Inject(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/Inventory;)V", at = @At("TAIL"))
	public void HopperScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, CallbackInfo ci) {
		this.inventorysizeattributes$updateActiveHotbarSlots(playerInventory.player);
		this.inventorysizeattributes$updateActiveInventorySlots(playerInventory.player);
	}

	@Override
	public void inventorysizeattributes$updateActiveHotbarSlots(PlayerEntity playerEntity) {
		for (int i = 32; i < 41; i++) {
			((SlotCustomization) this.slots.get(i)).slotcustomizationapi$setDisabledOverride(i >= 32 + InventorySizeAttributes.getActiveHotbarSlotAmount(playerEntity));
		}
	}

	@Override
	public void inventorysizeattributes$updateActiveInventorySlots(PlayerEntity playerEntity) {
		for (int i = 5; i < 32; i++) {
			((SlotCustomization) this.slots.get(i)).slotcustomizationapi$setDisabledOverride(i >= 5 + InventorySizeAttributes.getActiveInventorySlotAmount(playerEntity));
		}
	}
}
