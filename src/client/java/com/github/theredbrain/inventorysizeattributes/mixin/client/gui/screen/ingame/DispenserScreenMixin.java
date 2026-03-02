package com.github.theredbrain.inventorysizeattributes.mixin.client.gui.screen.ingame;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributes;
import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributesClient;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.DispenserScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.DispenserMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DispenserScreen.class)
public abstract class DispenserScreenMixin extends AbstractContainerScreen<DispenserMenu> {

	@Unique
	private static final Identifier SLOT_TEXTURE = Identifier.withDefaultNamespace("textures/gui/sprites/container/slot.png");

	public DispenserScreenMixin(DispenserMenu handler, Inventory inventory, Component title) {
		super(handler, inventory, title);
	}

	@WrapOperation(method = "renderBg", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIII)V"))
	protected void inventorysizeattributes$drawBackground(GuiGraphics instance, RenderPipeline pipeline, Identifier sprite, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight, Operation<Void> original) {
		if (InventorySizeAttributesClient.CLIENT_CONFIG.show_inactive_inventory_slots.get()) {
			original.call(instance, pipeline, sprite, x, y, u, v, width, height, textureWidth, textureHeight);
		} else {
			instance.blit(pipeline, InventorySizeAttributes.identifier("textures/gui/container/dispenser_no_slots.png"), x, y, u, v, width, height, textureWidth, textureHeight);

			int inventorySize = 0;
			int hotbarSize = 0;
			if (this.minecraft != null && this.minecraft.player != null) {
				hotbarSize = InventorySizeAttributes.getActiveHotbarSlotAmount(this.minecraft.player);
				inventorySize = InventorySizeAttributes.getActiveInventorySlotAmount(this.minecraft.player);
			}
			for (int i = 0; i < (Math.min(inventorySize, 27)); ++i) {
				int j = (i / 9);
				instance.blit(pipeline, SLOT_TEXTURE, x + 7 + (i - (j * 9)) * 18, y + 83 + (j * 18), 0, 0, 18, 18, 18, 18);
			}
			for (int i = 0; i < (Math.min(hotbarSize, 9)); ++i) {
				instance.blit(pipeline, SLOT_TEXTURE, x + 7 + i * 18, y + 141, 0, 0, 18, 18, 18, 18);
			}
		}
	}
}
