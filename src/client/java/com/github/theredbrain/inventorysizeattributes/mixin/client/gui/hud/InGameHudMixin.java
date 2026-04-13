package com.github.theredbrain.inventorysizeattributes.mixin.client.gui.hud;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributesClient;
import com.github.theredbrain.inventorysizeattributes.gui.hud.DuckInGameHudMixin;
import com.github.theredbrain.inventorysizeattributes.gui.hud.InGameHudHelper;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin implements DuckInGameHudMixin {

	@Shadow
	protected abstract void renderHotbarItem(DrawContext context, int x, int y, RenderTickCounter tickCounter, PlayerEntity player, ItemStack stack, int seed);

	@Shadow
	@Nullable
	protected abstract PlayerEntity getCameraPlayer();

	@Shadow
	@Final
	private MinecraftClient client;

	@WrapOperation(
			method = "renderMainHud",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V")
	)
	private void inventorysizeattributes$wrap_renderHotbar(InGameHud instance, DrawContext context, RenderTickCounter tickCounter, Operation<Void> original) {
		if (InventorySizeAttributesClient.CLIENT_CONFIG.show_inactive_hotbar_slots.get()) {
			original.call(instance, context, tickCounter);
		} else {
			InGameHudHelper.inventorysizeattributes$renderOverhauledItemHotbar(instance, context, tickCounter);
		}
	}

	@Nullable
	@Override
	public PlayerEntity inventorysizeattributes$cameraPlayerAccessor() {
		return this.getCameraPlayer();
	}

	@Override
	public MinecraftClient inventorysizeattributes$clientAccessor() {
		return this.client;
	}

	@Override
	public void inventorysizeattributes$renderSlot_Invoker(DrawContext context, int x, int y, RenderTickCounter tickCounter, PlayerEntity player, ItemStack stack, int seed) {
		this.renderHotbarItem(context, x, y, tickCounter, player, stack, seed);
	}
}
