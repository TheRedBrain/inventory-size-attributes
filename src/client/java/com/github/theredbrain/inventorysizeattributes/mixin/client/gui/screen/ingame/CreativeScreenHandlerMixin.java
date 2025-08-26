package com.github.theredbrain.inventorysizeattributes.mixin.client.gui.screen.ingame;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributes;
import com.github.theredbrain.inventorysizeattributes.screen.DuckScreenHandlerMixin;
import com.github.theredbrain.slotcustomizationapi.api.SlotCustomization;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CreativeInventoryScreen.CreativeScreenHandler.class)
public abstract class CreativeScreenHandlerMixin extends ScreenHandler implements DuckScreenHandlerMixin {
	public CreativeScreenHandlerMixin(ScreenHandlerType<?> screenHandlerType, int i) {
		super(screenHandlerType, i);
	}

	@Override
	public void inventorysizeattributes$updateActiveHotbarSlots(PlayerEntity playerEntity) {
		int startIndex = this.slots.size() - 9;
		for (int i = startIndex; i < startIndex + 9; i++) {
			((SlotCustomization) this.slots.get(i)).slotcustomizationapi$setDisabledOverride(i >= startIndex + InventorySizeAttributes.getActiveHotbarSlotAmount(playerEntity));
		}
	}
}
