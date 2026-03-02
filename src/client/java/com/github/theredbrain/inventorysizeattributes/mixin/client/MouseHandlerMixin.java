package com.github.theredbrain.inventorysizeattributes.mixin.client;

import com.github.theredbrain.inventorysizeattributes.entity.player.DuckPlayerEntityMixin;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

	@Shadow
	@Final
	private Minecraft minecraft;

	@WrapOperation(method = "onScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;getSelectionSize()I"))
	private int inventorysizeattributes$wrap_getSelectionSize(Operation<Integer> original) {
		if (this.minecraft.player != null) {
			return ((DuckPlayerEntityMixin) this.minecraft.player).inventorysizeattributes$getActiveHotbarSlotAmount();
		} else {
			return original.call();
		}
	}

}
