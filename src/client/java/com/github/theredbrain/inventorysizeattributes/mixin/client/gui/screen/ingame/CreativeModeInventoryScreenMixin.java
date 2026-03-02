package com.github.theredbrain.inventorysizeattributes.mixin.client.gui.screen.ingame;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributes;
import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributesClient;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin extends AbstractContainerScreen<CreativeModeInventoryScreen.ItemPickerMenu> {

	@Shadow
	private static CreativeModeTab selectedTab;

	@Unique
	private static final Identifier SLOT_TEXTURE = Identifier.withDefaultNamespace("textures/gui/sprites/container/slot.png");

	public CreativeModeInventoryScreenMixin(CreativeModeInventoryScreen.ItemPickerMenu handler, Inventory inventory, Component title) {
		super(handler, inventory, title);
	}

	@WrapOperation(method = "renderBg", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIII)V"))
	protected void inventorysizeattributes$drawBackground(GuiGraphics instance, RenderPipeline pipeline, Identifier sprite, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight, Operation<Void> original) {
		if (InventorySizeAttributesClient.CLIENT_CONFIG.show_inactive_inventory_slots.get()) {
			original.call(instance, pipeline, sprite, x, y, u, v, width, height, textureWidth, textureHeight);
		} else {
			String string = sprite.getPath();
			instance.blit(pipeline, InventorySizeAttributes.identifier(string.substring(0, string.length() - 4) + "_no_slots.png"), x, y, u, v, width, height, textureWidth, textureHeight);

			int inventorySize = 0;
			int hotbarSize = 0;
			if (this.minecraft != null && this.minecraft.player != null) {
				hotbarSize = InventorySizeAttributes.getActiveHotbarSlotAmount(this.minecraft.player);
				inventorySize = InventorySizeAttributes.getActiveInventorySlotAmount(this.minecraft.player);
			}
			if (selectedTab.getType() == CreativeModeTab.Type.INVENTORY) {
				for (int i = 0; i < (Math.min(inventorySize, 27)); ++i) {
					int j = (i / 9);
					instance.blit(pipeline, SLOT_TEXTURE, x + 8 + (i - (j * 9)) * 18, y + 53 + (j * 18), 0, 0, 18, 18, 18, 18);
				}
			}
			for (int i = 0; i < (Math.min(hotbarSize, 9)); ++i) {
				instance.blit(pipeline, SLOT_TEXTURE, x + 8 + i * 18, y + 111, 0, 0, 18, 18, 18, 18);
			}
		}
	}
}
