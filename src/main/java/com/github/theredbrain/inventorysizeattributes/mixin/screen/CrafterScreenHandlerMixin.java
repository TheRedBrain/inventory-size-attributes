package com.github.theredbrain.inventorysizeattributes.mixin.screen;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributes;
import com.github.theredbrain.inventorysizeattributes.screen.DuckScreenHandlerMixin;
import com.github.theredbrain.slotcustomizationapi.api.SlotCustomization;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.CrafterScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CrafterScreenHandler.class)
public abstract class CrafterScreenHandlerMixin extends ScreenHandler implements DuckScreenHandlerMixin {
	protected CrafterScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
		super(type, syncId);
	}

	@Inject(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;)V", at = @At("TAIL"))
	public void CrafterScreenHandler(int syncId, PlayerInventory playerInventory, CallbackInfo ci) {
		this.inventorysizeattributes$updateActiveHotbarSlots(playerInventory.player);
		this.inventorysizeattributes$updateActiveInventorySlots(playerInventory.player);
	}

	@Override
	public void inventorysizeattributes$updateActiveHotbarSlots(PlayerEntity playerEntity) {
		for (int i = 37; i < 46; i++) {
			((SlotCustomization) this.slots.get(i)).slotcustomizationapi$setDisabledOverride(i >= 37 + InventorySizeAttributes.getActiveHotbarSlotAmount(playerEntity));
		}
	}

	@Override
	public void inventorysizeattributes$updateActiveInventorySlots(PlayerEntity playerEntity) {
		for (int i = 10; i < 37; i++) {
			((SlotCustomization) this.slots.get(i)).slotcustomizationapi$setDisabledOverride(i >= 10 + InventorySizeAttributes.getActiveInventorySlotAmount(playerEntity));
		}
	}
}
