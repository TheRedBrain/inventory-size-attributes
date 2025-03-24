package com.github.theredbrain.inventorysizeattributes.mixin.client.gui.screen.ingame;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributes;
import com.github.theredbrain.inventorysizeattributes.gui.screen.ingame.HasBackgroundWithNoSlots;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AnvilScreen.class)
public class AnvilScreenMixin implements HasBackgroundWithNoSlots {

	public Identifier inventorysizeattributes$getBackgroundWithNoSlots() {
		return InventorySizeAttributes.identifier("textures/gui/container/anvil_no_slots.png");
	}
}
