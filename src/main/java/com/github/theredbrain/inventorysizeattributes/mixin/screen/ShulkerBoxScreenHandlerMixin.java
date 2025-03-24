package com.github.theredbrain.inventorysizeattributes.mixin.screen;

import com.github.theredbrain.inventorysizeattributes.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.slotcustomizationapi.api.SlotCustomization;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShulkerBoxScreenHandler.class)
public abstract class ShulkerBoxScreenHandlerMixin extends ScreenHandler {
	public ShulkerBoxScreenHandlerMixin(ScreenHandlerType<?> screenHandlerType, int i) {
		super(screenHandlerType, i);
	}

	@Inject(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/Inventory;)V", at = @At("TAIL"))
	public void ShulkerBoxScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, CallbackInfo ci) {
		for (int i = 27; i < 54; i++) {
			((SlotCustomization) this.slots.get(i)).slotcustomizationapi$setDisabledOverride(i >= 27 + ((DuckPlayerEntityMixin) playerInventory.player).inventorysizeattributes$getActiveHotbarSlotAmount());
		}
		for (int i = 54; i < 63; i++) {
			((SlotCustomization) this.slots.get(i)).slotcustomizationapi$setDisabledOverride(i >= 54 + ((DuckPlayerEntityMixin) playerInventory.player).inventorysizeattributes$getActiveInventorySlotAmount());
		}
	}
}
