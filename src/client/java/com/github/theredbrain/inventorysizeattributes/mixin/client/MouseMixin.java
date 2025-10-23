package com.github.theredbrain.inventorysizeattributes.mixin.client;

import com.github.theredbrain.inventorysizeattributes.entity.player.DuckPlayerEntityMixin;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Mouse.class)
public class MouseMixin {

	@Shadow
	@Final
	private MinecraftClient client;

	@WrapOperation(method = "onMouseScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;getHotbarSize()I"))
	private int inventorysizeattributes$wrap_getHotbarSize(Operation<Integer> original) {
		if (this.client.player != null) {
			return ((DuckPlayerEntityMixin) this.client.player).inventorysizeattributes$getActiveHotbarSlotAmount();
		} else {
			return original.call();
		}
	}

}
