package com.github.theredbrain.inventorysizeattributes.gui.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface DuckInGameHudMixin {
	@Nullable
	PlayerEntity inventorysizeattributes$cameraPlayerAccessor();

	MinecraftClient inventorysizeattributes$clientAccessor();

	void inventorysizeattributes$renderSlot_Invoker(DrawContext context, int x, int y, RenderTickCounter tickCounter, PlayerEntity player, ItemStack stack, int seed);
}
