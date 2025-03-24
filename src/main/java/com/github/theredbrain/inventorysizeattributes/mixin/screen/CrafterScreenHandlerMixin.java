package com.github.theredbrain.inventorysizeattributes.mixin.screen;

import com.github.theredbrain.inventorysizeattributes.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.slotcustomizationapi.api.SlotCustomization;
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
public abstract class CrafterScreenHandlerMixin extends ScreenHandler {
	protected CrafterScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
		super(type, syncId);
	}

	@Inject(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;)V", at = @At("TAIL"))
	public void CrafterScreenHandler(int syncId, PlayerInventory playerInventory, CallbackInfo ci) {
		for (int i = 37; i < 46; i++) {
			((SlotCustomization) this.slots.get(i)).slotcustomizationapi$setDisabledOverride(i >= 37 + ((DuckPlayerEntityMixin) playerInventory.player).inventorysizeattributes$getActiveHotbarSlotAmount());
		}
		for (int i = 10; i < 37; i++) {
			((SlotCustomization) this.slots.get(i)).slotcustomizationapi$setDisabledOverride(i >= 10 + ((DuckPlayerEntityMixin) playerInventory.player).inventorysizeattributes$getActiveInventorySlotAmount());
		}
	}
}
