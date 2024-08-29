package com.github.theredbrain.inventorysizeattributes.mixin.screen;

import com.github.theredbrain.inventorysizeattributes.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.slotcustomizationapi.api.SlotCustomization;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.BeaconScreenHandler;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeaconScreenHandler.class)
public abstract class BeaconScreenHandlerMixin extends ScreenHandler {
	public BeaconScreenHandlerMixin(ScreenHandlerType<?> screenHandlerType, int i) {
		super(screenHandlerType, i);
	}

	@Inject(method = "<init>(ILnet/minecraft/inventory/Inventory;Lnet/minecraft/screen/PropertyDelegate;Lnet/minecraft/screen/ScreenHandlerContext;)V", at = @At("TAIL"))
	public void BeaconScreenHandler(int syncId, Inventory inventory, PropertyDelegate propertyDelegate, ScreenHandlerContext context, CallbackInfo ci) {
		if (inventory instanceof PlayerInventory playerInventory) {
			for (int i = 28; i < 37; i++) {
				((SlotCustomization) this.slots.get(i)).slotcustomizationapi$setDisabledOverride(i >= 28 + ((DuckPlayerEntityMixin) playerInventory.player).inventorysizeattributes$getActiveHotbarSlotAmount());
			}
			for (int i = 1; i < 28; i++) {
				((SlotCustomization) this.slots.get(i)).slotcustomizationapi$setDisabledOverride(i >= 1 + ((DuckPlayerEntityMixin) playerInventory.player).inventorysizeattributes$getActiveInventorySlotAmount());
			}
		}
	}
}
