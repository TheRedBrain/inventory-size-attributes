package com.github.theredbrain.inventorysizeattributes.mixin.client.gui.screen.ingame;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributes;
import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributesClient;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin extends HandledScreen<CreativeInventoryScreen.CreativeScreenHandler> {

	@Shadow
	private static ItemGroup selectedTab;

	@Unique
	private static final Identifier SLOT_TEXTURE = Identifier.ofVanilla("textures/gui/sprites/container/slot.png");

	public CreativeInventoryScreenMixin(CreativeInventoryScreen.CreativeScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@WrapOperation(method = "drawBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIFFIIII)V"))
	protected void inventorysizeattributes$drawBackground(DrawContext instance, RenderPipeline pipeline, Identifier sprite, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight, Operation<Void> original) {
		if (InventorySizeAttributesClient.CLIENT_CONFIG.show_inactive_inventory_slots.get()) {
			original.call(instance, pipeline, sprite, x, y, u, v, width, height, textureWidth, textureHeight);
		} else {
			String string = sprite.getPath();
			instance.drawTexture(pipeline, InventorySizeAttributes.identifier(string.substring(0, string.length() - 4) + "_no_slots.png"), x, y, u, v, width, height, textureWidth, textureHeight);

			int inventorySize = 0;
			int hotbarSize = 0;
			if (this.client != null && this.client.player != null) {
				hotbarSize = InventorySizeAttributes.getActiveHotbarSlotAmount(this.client.player);
				inventorySize = InventorySizeAttributes.getActiveInventorySlotAmount(this.client.player);
			}
			if (selectedTab.getType() == ItemGroup.Type.INVENTORY) {
				for (int i = 0; i < (Math.min(inventorySize, 27)); ++i) {
					int j = (i / 9);
					instance.drawTexture(pipeline, SLOT_TEXTURE, x + 8 + (i - (j * 9)) * 18, y + 53 + (j * 18), 0, 0, 18, 18, 18, 18);
				}
			}
			for (int i = 0; i < (Math.min(hotbarSize, 9)); ++i) {
				instance.drawTexture(pipeline, SLOT_TEXTURE, x + 8 + i * 18, y + 111, 0, 0, 18, 18, 18, 18);
			}
		}
	}
}
