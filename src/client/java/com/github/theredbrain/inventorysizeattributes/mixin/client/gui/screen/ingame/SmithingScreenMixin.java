package com.github.theredbrain.inventorysizeattributes.mixin.client.gui.screen.ingame;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributes;
import com.github.theredbrain.inventorysizeattributes.gui.screen.ingame.HasBackgroundWithNoSlots;
import net.minecraft.client.gui.screen.ingame.SmithingScreen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SmithingScreen.class)
public class SmithingScreenMixin implements HasBackgroundWithNoSlots {

	public Identifier inventorysizeattributes$getBackgroundWithNoSlots() {
		return InventorySizeAttributes.identifier("textures/gui/container/smithing_no_slots.png");
	}
}
