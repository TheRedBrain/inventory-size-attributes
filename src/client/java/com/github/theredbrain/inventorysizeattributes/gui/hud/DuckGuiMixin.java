package com.github.theredbrain.inventorysizeattributes.gui.hud;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface DuckGuiMixin {
	@Nullable
	Player inventorysizeattributes$cameraPlayerAccessor();

	Minecraft inventorysizeattributes$minecraftAccessor();

	void inventorysizeattributes$renderSlot_Invoker(final GuiGraphicsExtractor graphics, final int x, final int y, final DeltaTracker deltaTracker, final Player player, final ItemStack itemStack, final int seed);
}
