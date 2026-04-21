package com.github.theredbrain.inventorysizeattributes.mixin.screen;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributes;
import com.github.theredbrain.inventorysizeattributes.screen.DuckMenuMixin;
import com.github.theredbrain.slotcustomizationapi.api.SlotCustomization;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemCombinerMenu.class)
public abstract class ItemCombinerMenuMixin extends AbstractContainerMenu implements DuckMenuMixin {

	protected ItemCombinerMenuMixin(@Nullable MenuType<?> type, int syncId) {
		super(type, syncId);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	public void ItemCombinerMenu(MenuType<?> menuType, int containerId, Inventory inventory, ContainerLevelAccess access, ItemCombinerMenuSlotDefinition itemInputSlots, CallbackInfo ci) {
		this.inventorysizeattributes$updateActiveHotbarSlots(inventory.player);
		this.inventorysizeattributes$updateActiveInventorySlots(inventory.player);
	}

	@Override
	public void inventorysizeattributes$updateActiveHotbarSlots(Player playerEntity) {
		int startIndex = this.slots.size() - 36;
		for (int i = startIndex + 27; i < startIndex + 36; i++) {
			((SlotCustomization) this.slots.get(i)).slotcustomizationapi$setDisabledOverride(i >= startIndex + 27 + InventorySizeAttributes.getActiveHotbarSlotAmount(playerEntity));
		}
	}

	@Override
	public void inventorysizeattributes$updateActiveInventorySlots(Player playerEntity) {
		int startIndex = this.slots.size() - 36;
		for (int i = startIndex; i < startIndex + 27; i++) {
			((SlotCustomization) this.slots.get(i)).slotcustomizationapi$setDisabledOverride(i >= startIndex + InventorySizeAttributes.getActiveInventorySlotAmount(playerEntity));
		}
	}
}
