package com.github.theredbrain.inventorysizeattributes.mixin.client.gui.screen.ingame;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributes;
import com.github.theredbrain.inventorysizeattributes.screen.DuckMenuMixin;
import com.github.theredbrain.slotcustomizationapi.api.SlotCustomization;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CreativeModeInventoryScreen.ItemPickerMenu.class)
public abstract class CreativeModeInventoryScreenItemPickerMenuMixin extends AbstractContainerMenu implements DuckMenuMixin {
	public CreativeModeInventoryScreenItemPickerMenuMixin(MenuType<?> screenHandlerType, int i) {
		super(screenHandlerType, i);
	}

	@Override
	public void inventorysizeattributes$updateActiveHotbarSlots(Player playerEntity) {
		int startIndex = this.slots.size() - 9;
		for (int i = startIndex; i < startIndex + 9; i++) {
			((SlotCustomization) this.slots.get(i)).slotcustomizationapi$setDisabledOverride(i >= startIndex + InventorySizeAttributes.getActiveHotbarSlotAmount(playerEntity));
		}
	}
}
