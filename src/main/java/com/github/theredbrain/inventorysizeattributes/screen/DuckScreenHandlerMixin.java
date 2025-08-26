package com.github.theredbrain.inventorysizeattributes.screen;

import net.minecraft.entity.player.PlayerEntity;

public interface DuckScreenHandlerMixin {
	default void inventorysizeattributes$updateActiveHotbarSlots(PlayerEntity playerEntity) {
	}

	default void inventorysizeattributes$updateActiveInventorySlots(PlayerEntity playerEntity) {
	}
}
