package com.github.theredbrain.inventorysizeattributes.mixin.server.network;

import com.github.theredbrain.inventorysizeattributes.entity.player.DuckPlayerEntityMixin;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.OptionalInt;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin implements DuckPlayerEntityMixin {

	@Inject(method = "openHandledScreen", at = @At("TAIL"))
	protected void inventorysizeattributes$openHandledScreen(NamedScreenHandlerFactory factory, CallbackInfoReturnable<OptionalInt> cir) {
		this.inventorysizeattributes$setOldHotbarSlotAmount(-1);
		this.inventorysizeattributes$setOldInventorySlotAmount(-1);
	}

	@Inject(method = "onHandledScreenClosed", at = @At("TAIL"))
	protected void inventorysizeattributes$onHandledScreenClosed(CallbackInfo ci) {
		this.inventorysizeattributes$setOldHotbarSlotAmount(-1);
		this.inventorysizeattributes$setOldInventorySlotAmount(-1);
	}

}
