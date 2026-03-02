package com.github.theredbrain.inventorysizeattributes.mixin.client.gui.hud;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributesClient;
import com.github.theredbrain.inventorysizeattributes.gui.hud.DuckGuiMixin;
import com.github.theredbrain.inventorysizeattributes.gui.hud.GuiHelper;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(Gui.class)
public abstract class GuiMixin implements DuckGuiMixin {

	@Shadow
	@org.jspecify.annotations.Nullable
	protected abstract Player getCameraPlayer();

	@Shadow
	@Final
	private Minecraft minecraft;

	@Shadow
	protected abstract void renderSlot(GuiGraphics guiGraphics, int i, int j, DeltaTracker deltaTracker, Player player, net.minecraft.world.item.ItemStack itemStack, int k);

	@WrapOperation(
			method = "renderHotbarAndDecorations",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderItemHotbar(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/DeltaTracker;)V")
	)
	private void inventorysizeattributes$wrap_renderItemHotbar(Gui instance, GuiGraphics guiGraphics, DeltaTracker deltaTracker, Operation<Void> original) {
		if (InventorySizeAttributesClient.CLIENT_CONFIG.show_inactive_hotbar_slots.get()) {
			original.call(instance, guiGraphics, deltaTracker);
		} else {
			GuiHelper.inventorysizeattributes$renderOverhauledItemHotbar(instance, guiGraphics, deltaTracker);
		}
	}

	@Nullable
	@Override
	public Player inventorysizeattributes$cameraPlayerAccessor() {
		return this.getCameraPlayer();
	}

	@Override
	public Minecraft inventorysizeattributes$minecraftAccessor() {
		return this.minecraft;
	}

	@Override
	public void inventorysizeattributes$renderSlot_Invoker(GuiGraphics guiGraphics, int i, int j, DeltaTracker deltaTracker, Player player, ItemStack itemStack, int k) {
		this.renderSlot(guiGraphics, i, j, deltaTracker, player, itemStack, k);
	}
}
