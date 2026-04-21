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
import net.minecraft.client.gui.GuiGraphicsExtractor;
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
	protected abstract void extractSlot(final GuiGraphicsExtractor graphics, final int x, final int y, final DeltaTracker deltaTracker, final Player player, final ItemStack itemStack, final int seed);

	@WrapOperation(
			method = "extractHotbarAndDecorations",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;extractItemHotbar(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V")
	)
	private void inventorysizeattributes$wrap_extractItemHotbar(Gui instance, GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, Operation<Void> original) {
		if (InventorySizeAttributesClient.CLIENT_CONFIG.show_inactive_hotbar_slots.get()) {
			original.call(instance, graphics, deltaTracker);
		} else {
			GuiHelper.inventorysizeattributes$extractOverhauledItemHotbar(instance, graphics, deltaTracker);
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
	public void inventorysizeattributes$renderSlot_Invoker(final GuiGraphicsExtractor graphics, final int x, final int y, final DeltaTracker deltaTracker, final Player player, final ItemStack itemStack, final int seed) {
		this.extractSlot(graphics, x, y, deltaTracker, player, itemStack, seed);
	}
}
