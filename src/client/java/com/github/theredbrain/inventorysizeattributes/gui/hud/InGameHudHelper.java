package com.github.theredbrain.inventorysizeattributes.gui.hud;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributes;
import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributesClient;
import com.github.theredbrain.inventorysizeattributes.config.ClientConfig;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;

public class InGameHudHelper {
	private static final Identifier HOTBAR_TEXTURE = Identifier.ofVanilla("hud/hotbar");
	private static final Identifier HOTBAR_OFFHAND_LEFT_TEXTURE = Identifier.ofVanilla("hud/hotbar_offhand_left");
	private static final Identifier HOTBAR_OFFHAND_RIGHT_TEXTURE = Identifier.ofVanilla("hud/hotbar_offhand_right");
	private static final Identifier HOTBAR_ATTACK_INDICATOR_BACKGROUND_TEXTURE = Identifier.ofVanilla("hud/hotbar_attack_indicator_background");
	private static final Identifier HOTBAR_ATTACK_INDICATOR_PROGRESS_TEXTURE = Identifier.ofVanilla("hud/hotbar_attack_indicator_progress");

	private static final Identifier HOTBAR_SELECTION_FIXED_TEXTURE = InventorySizeAttributes.identifier("hud/hotbar_selection_fixed");

	public static void inventorysizeattributes$renderOverhauledItemHotbar(InGameHud inGameHud, DrawContext context, RenderTickCounter tickCounter) {

		// TODO add support for Raised
		PlayerEntity playerEntity = ((DuckInGameHudMixin) inGameHud).inventorysizeattributes$cameraPlayerAccessor();
		MinecraftClient minecraftClient = ((DuckInGameHudMixin) inGameHud).inventorysizeattributes$clientAccessor();
		if (playerEntity != null) {
			ClientConfig clientConfig = InventorySizeAttributesClient.CLIENT_CONFIG;
			ItemStack itemStack = playerEntity.getOffHandStack();
			Arm arm = playerEntity.getMainArm().getOpposite();
			int i = context.getScaledWindowWidth() / 2;
			int j = 182;
			int k = 91;
			RenderSystem.enableBlend();
			context.getMatrices().push();
			context.getMatrices().translate(0.0F, 0.0F, -90.0F);

			int hotbar_start_x = i - 91;
			int hotbar_width = 182;

			int activeHotbarSize = InventorySizeAttributes.getActiveHotbarSlotAmount(playerEntity);
			if (clientConfig.show_inactive_hotbar_slots.get() || activeHotbarSize == 9) {
				context.drawGuiTexture(HOTBAR_TEXTURE, hotbar_start_x, context.getScaledWindowHeight() - 22, hotbar_width, 22);

			} else if (activeHotbarSize > 0) {
				if (clientConfig.is_hotbar_centered.get()) {
					hotbar_start_x = hotbar_start_x + ((9 - activeHotbarSize) * 20) / 2;
				}
				context.drawGuiTexture(InventorySizeAttributes.identifier("hud/hotbar_" + activeHotbarSize), hotbar_start_x, context.getScaledWindowHeight() - 22, 182 - (9 - activeHotbarSize) * 20, 22);
			}

			context.drawGuiTexture(
					HOTBAR_SELECTION_FIXED_TEXTURE, hotbar_start_x - 1 + playerEntity.getInventory().selectedSlot * 20, context.getScaledWindowHeight() - 22 - 1, 24, 24
			);

			if (!itemStack.isEmpty()) {
				if (arm == Arm.LEFT) {
					context.drawGuiTexture(HOTBAR_OFFHAND_LEFT_TEXTURE, i - 91 - 29, context.getScaledWindowHeight() - 23, 29, 24);
				} else {
					context.drawGuiTexture(HOTBAR_OFFHAND_RIGHT_TEXTURE, i - 91 + 182, context.getScaledWindowHeight() - 23, 29, 24);
				}
			}

			context.getMatrices().pop();
			RenderSystem.disableBlend();
			int l = 1;

			for (int m = 0; m < activeHotbarSize; m++) {
				int n = hotbar_start_x + 1 + m * 20 + 2;
				int o = context.getScaledWindowHeight() - 16 - 3;
				((DuckInGameHudMixin) inGameHud).inventorysizeattributes$renderSlot_Invoker(context, n, o, tickCounter, playerEntity, playerEntity.getInventory().main.get(m), l++);
			}

			if (!itemStack.isEmpty()) {
				int m = context.getScaledWindowHeight() - 16 - 3;
				if (arm == Arm.LEFT) {
					((DuckInGameHudMixin) inGameHud).inventorysizeattributes$renderSlot_Invoker(context, i - 91 - 26, m, tickCounter, playerEntity, itemStack, l++);
				} else {
					((DuckInGameHudMixin) inGameHud).inventorysizeattributes$renderSlot_Invoker(context, i - 91 + 182 + 10, m, tickCounter, playerEntity, itemStack, l++);
				}
			}

			if (minecraftClient.options.getAttackIndicator().getValue() == AttackIndicator.HOTBAR) {
				ClientPlayerEntity clientPlayerEntity = minecraftClient.player;
				if (clientPlayerEntity != null) {
					RenderSystem.enableBlend();
					float f = clientPlayerEntity.getAttackCooldownProgress(0.0F);
					if (f < 1.0F) {
						int n = context.getScaledWindowHeight() - 20;
						int o = i + 91 + 6;
						if (arm == Arm.RIGHT) {
							o = i - 91 - 22;
						}

						int p = (int) (f * 19.0F);
						context.drawGuiTexture(HOTBAR_ATTACK_INDICATOR_BACKGROUND_TEXTURE, o, n, 18, 18);
						context.drawGuiTexture(HOTBAR_ATTACK_INDICATOR_PROGRESS_TEXTURE, 18, 18, 0, 18 - p, o, n + 18 - p, 18, p);
					}
				}

				RenderSystem.disableBlend();
			}
		}
	}

}
