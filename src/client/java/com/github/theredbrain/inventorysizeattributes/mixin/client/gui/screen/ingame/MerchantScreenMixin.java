package com.github.theredbrain.inventorysizeattributes.mixin.client.gui.screen.ingame;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributes;
import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributesClient;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MerchantScreen.class)
public abstract class MerchantScreenMixin extends HandledScreen<MerchantScreenHandler> {

	@Unique
	private static final Identifier SLOT_TEXTURE = Identifier.ofVanilla("textures/gui/sprites/container/slot.png");

	public MerchantScreenMixin(MerchantScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@WrapOperation(method = "drawBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIFFIIII)V"))
	protected void inventorysizeattributes$drawBackground(DrawContext instance, Identifier texture, int x, int y, int z, float u, float v, int width, int height, int textureWidth, int textureHeight, Operation<Void> original) {
		if (InventorySizeAttributesClient.CLIENT_CONFIG.show_inactive_inventory_slots.get()) {
			original.call(instance, texture, x, y, z, u, v, width, height, textureWidth, textureHeight);
		} else {
			instance.drawTexture(InventorySizeAttributes.identifier("textures/gui/container/villager_no_slots.png"), x, y, z, u, v, width, height, textureWidth, textureHeight);

			int inventorySize = 0;
			int hotbarSize = 0;
			if (this.client != null && this.client.player != null) {
				hotbarSize = InventorySizeAttributes.getActiveHotbarSlotAmount(this.client.player);
				inventorySize = InventorySizeAttributes.getActiveInventorySlotAmount(this.client.player);
			}
			for (int i = 0; i < (Math.min(inventorySize, 27)); ++i) {
				int j = (i / 9);
				instance.drawTexture(SLOT_TEXTURE, x + 107 + (i - (j * 9)) * 18, y + 83 + (j * 18), 0, 0, 18, 18, 18, 18);
			}
			for (int i = 0; i < (Math.min(hotbarSize, 9)); ++i) {
				instance.drawTexture(SLOT_TEXTURE, x + 107 + i * 18, y + 141, 0, 0, 18, 18, 18, 18);
			}
		}
	}
}
