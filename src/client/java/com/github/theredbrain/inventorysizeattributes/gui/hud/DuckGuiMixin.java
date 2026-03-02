package com.github.theredbrain.inventorysizeattributes.gui.hud;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface DuckGuiMixin {
	@Nullable
	Player inventorysizeattributes$cameraPlayerAccessor();

	Minecraft inventorysizeattributes$minecraftAccessor();

	void inventorysizeattributes$renderSlot_Invoker(GuiGraphics guiGraphics, int i, int j, DeltaTracker deltaTracker, Player player, ItemStack itemStack, int k);
}
