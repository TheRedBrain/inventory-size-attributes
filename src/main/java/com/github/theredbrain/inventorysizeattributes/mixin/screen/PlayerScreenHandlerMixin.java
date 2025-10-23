package com.github.theredbrain.inventorysizeattributes.mixin.screen;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributes;
import com.github.theredbrain.inventorysizeattributes.screen.DuckScreenHandlerMixin;
import com.github.theredbrain.slotcustomizationapi.api.SlotCustomization;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin extends AbstractRecipeScreenHandler implements DuckScreenHandlerMixin {

	public PlayerScreenHandlerMixin(ScreenHandlerType<?> screenHandlerType, int i) {
		super(screenHandlerType, i);
	}

	@Override
	public void inventorysizeattributes$updateActiveHotbarSlots(PlayerEntity playerEntity) {
		for (int i = 36; i < 45; i++) {
			((SlotCustomization) this.slots.get(i)).slotcustomizationapi$setDisabledOverride(i >= 36 + InventorySizeAttributes.getActiveHotbarSlotAmount(playerEntity));
		}
	}

	@Override
	public void inventorysizeattributes$updateActiveInventorySlots(PlayerEntity playerEntity) {
		for (int i = 9; i < 36; i++) {
			((SlotCustomization) this.slots.get(i)).slotcustomizationapi$setDisabledOverride(i >= 9 + InventorySizeAttributes.getActiveInventorySlotAmount(playerEntity));
		}
	}
}
