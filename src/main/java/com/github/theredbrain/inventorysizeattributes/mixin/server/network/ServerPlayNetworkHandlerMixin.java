package com.github.theredbrain.inventorysizeattributes.mixin.server.network;

import com.github.theredbrain.inventorysizeattributes.entity.player.DuckPlayerEntityMixin;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {

	@Shadow
	public ServerPlayerEntity player;

	@WrapOperation(method = "onUpdateSelectedSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;getHotbarSize()I"))
	public int inventorysizeattributes$wrap_getHotbarSize(Operation<Integer> original) {
		return ((DuckPlayerEntityMixin) this.player).inventorysizeattributes$getActiveHotbarSlotAmount();
	}

}
