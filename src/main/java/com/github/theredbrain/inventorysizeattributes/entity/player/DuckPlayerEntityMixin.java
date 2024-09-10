package com.github.theredbrain.inventorysizeattributes.entity.player;

public interface DuckPlayerEntityMixin {
	int inventorysizeattributes$getActiveHotbarSlotAmount();

	int inventorysizeattributes$getHotbarSlotAmount();

	int inventorysizeattributes$getOldHotbarSlotAmount();

	void inventorysizeattributes$setOldHotbarSlotAmount(int hotbar_slot_amount);

	int inventorysizeattributes$getActiveInventorySlotAmount();

	int inventorysizeattributes$getInventorySlotAmount();

	int inventorysizeattributes$getOldInventorySlotAmount();

	void inventorysizeattributes$setOldInventorySlotAmount(int inventory_slot_amount);
}
