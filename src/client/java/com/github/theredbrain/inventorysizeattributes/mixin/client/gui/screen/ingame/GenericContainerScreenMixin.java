package com.github.theredbrain.inventorysizeattributes.mixin.client.gui.screen.ingame;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributes;
import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributesClient;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GenericContainerScreen.class)
public abstract class GenericContainerScreenMixin extends HandledScreen<GenericContainerScreenHandler> {

	@Unique
	private static final Identifier SLOT_TEXTURE = Identifier.ofVanilla("textures/gui/sprites/container/slot.png");

	public GenericContainerScreenMixin(GenericContainerScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@WrapOperation(method = "drawBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIFFIIII)V"))
	protected void inventorysizeattributes$drawBackground(DrawContext instance, RenderPipeline pipeline, Identifier sprite, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight, Operation<Void> original) {
		if (InventorySizeAttributesClient.CLIENT_CONFIG.show_inactive_inventory_slots.get()) {
			original.call(instance, pipeline, sprite, x, y, u, v, width, height, textureWidth, textureHeight);
		} else {
			instance.drawTexture(pipeline, InventorySizeAttributes.identifier("textures/gui/container/generic_54_no_slots.png"), x, y, u, v, width, height, textureWidth, textureHeight);

			int inventorySize = 0;
			int hotbarSize = 0;
			if (this.client != null && this.client.player != null) {
				hotbarSize = InventorySizeAttributes.getActiveHotbarSlotAmount(this.client.player);
				inventorySize = InventorySizeAttributes.getActiveInventorySlotAmount(this.client.player);
			}
			for (int k = 0; k < (Math.min(inventorySize, 27)); ++k) {
				int j = (k / 9);
				instance.drawTexture(pipeline, SLOT_TEXTURE, x + 7 + (k - (j * 9)) * 18, y + 13 + (j * 18), 0, 0, 18, 18, 18, 18);
			}
			for (int k = 0; k < (Math.min(hotbarSize, 9)); ++k) {
				instance.drawTexture(pipeline, SLOT_TEXTURE, x + 7 + k * 18, y + 71, 0, 0, 18, 18, 18, 18);
			}
		}
	}
}
