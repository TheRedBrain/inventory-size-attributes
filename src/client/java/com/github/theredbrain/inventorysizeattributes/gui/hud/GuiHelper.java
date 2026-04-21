package com.github.theredbrain.inventorysizeattributes.gui.hud;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributes;
import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributesClient;
import com.github.theredbrain.inventorysizeattributes.config.ClientConfig;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class GuiHelper {
	private static final Identifier HOTBAR_SPRITE = Identifier.withDefaultNamespace("hud/hotbar");
	private static final Identifier HOTBAR_OFFHAND_LEFT_SPRITE = Identifier.withDefaultNamespace("hud/hotbar_offhand_left");
	private static final Identifier HOTBAR_OFFHAND_RIGHT_SPRITE = Identifier.withDefaultNamespace("hud/hotbar_offhand_right");
	private static final Identifier HOTBAR_ATTACK_INDICATOR_BACKGROUND_SPRITE = Identifier.withDefaultNamespace("hud/hotbar_attack_indicator_background");
	private static final Identifier HOTBAR_ATTACK_INDICATOR_PROGRESS_SPRITE = Identifier.withDefaultNamespace("hud/hotbar_attack_indicator_progress");

	private static final Identifier HOTBAR_SELECTION_FIXED_SPRITE = InventorySizeAttributes.identifier("hud/hotbar_selection_fixed");

	public static void inventorysizeattributes$extractOverhauledItemHotbar(Gui gui, GuiGraphicsExtractor guiGraphicsExtractor, DeltaTracker deltaTracker) {

		// TODO add support for Raised
		Player player = ((DuckGuiMixin) gui).inventorysizeattributes$cameraPlayerAccessor();
		Minecraft minecraft = ((DuckGuiMixin) gui).inventorysizeattributes$minecraftAccessor();
		if (player != null) {
			ClientConfig clientConfig = InventorySizeAttributesClient.CLIENT_CONFIG;
			ItemStack itemStack = player.getOffhandItem();
			HumanoidArm humanoidArm = player.getMainArm().getOpposite();
			int i = guiGraphicsExtractor.guiWidth() / 2;
			int j = 182;
			int k = 91;

			int hotbar_start_x = i - 91;
			int hotbar_width = 182;

			int activeHotbarSize = InventorySizeAttributes.getActiveHotbarSlotAmount(player);
			if (activeHotbarSize == 9) {
				guiGraphicsExtractor.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_SPRITE, hotbar_start_x, guiGraphicsExtractor.guiHeight() - 22, hotbar_width, 22);

			} else if (activeHotbarSize > 0) {
				if (clientConfig.is_hotbar_centered.get()) {
					hotbar_start_x = hotbar_start_x + ((9 - activeHotbarSize) * 20) / 2;
				}
				guiGraphicsExtractor.blitSprite(RenderPipelines.GUI_TEXTURED, InventorySizeAttributes.identifier("hud/hotbar_" + activeHotbarSize), hotbar_start_x, guiGraphicsExtractor.guiHeight() - 22, 182 - (9 - activeHotbarSize) * 20, 22);
			}

			guiGraphicsExtractor.blitSprite(RenderPipelines.GUI_TEXTURED,
					HOTBAR_SELECTION_FIXED_SPRITE, hotbar_start_x - 1 + player.getInventory().getSelectedSlot() * 20, guiGraphicsExtractor.guiHeight() - 22 - 1, 24, 24
			);

			if (!itemStack.isEmpty()) {
				if (humanoidArm == HumanoidArm.LEFT) {
					guiGraphicsExtractor.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_OFFHAND_LEFT_SPRITE, i - 91 - 29, guiGraphicsExtractor.guiHeight() - 23, 29, 24);
				} else {
					guiGraphicsExtractor.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_OFFHAND_RIGHT_SPRITE, i + 91, guiGraphicsExtractor.guiHeight() - 23, 29, 24);
				}
			}

			int l = 1;

			for (int m = 0; m < activeHotbarSize; m++) {
				int n = hotbar_start_x + 1 + m * 20 + 2;
				int o = guiGraphicsExtractor.guiHeight() - 16 - 3;
				((DuckGuiMixin) gui).inventorysizeattributes$renderSlot_Invoker(guiGraphicsExtractor, n, o, deltaTracker, player, player.getInventory().getItem(m), l++);
			}

			if (!itemStack.isEmpty()) {
				int m = guiGraphicsExtractor.guiHeight() - 16 - 3;
				if (humanoidArm == HumanoidArm.LEFT) {
					((DuckGuiMixin) gui).inventorysizeattributes$renderSlot_Invoker(guiGraphicsExtractor, i - 91 - 26, m, deltaTracker, player, itemStack, l++);
				} else {
					((DuckGuiMixin) gui).inventorysizeattributes$renderSlot_Invoker(guiGraphicsExtractor, i + 91 + 10, m, deltaTracker, player, itemStack, l++);
				}
			}

			if (minecraft.options.attackIndicator().get() == AttackIndicatorStatus.HOTBAR) {
				LocalPlayer localPlayer = minecraft.player;
				if (localPlayer != null) {
					float f = localPlayer.getAttackStrengthScale(0.0F);
					if (f < 1.0F) {
						int n = guiGraphicsExtractor.guiHeight() - 20;
						int o = i + 91 + 6;
						if (humanoidArm == HumanoidArm.RIGHT) {
							o = i - 91 - 22;
						}

						int p = (int) (f * 19.0F);
						guiGraphicsExtractor.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_ATTACK_INDICATOR_BACKGROUND_SPRITE, o, n, 18, 18);
						guiGraphicsExtractor.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_ATTACK_INDICATOR_PROGRESS_SPRITE, 18, 18, 0, 18 - p, o, n + 18 - p, 18, p);
					}
				}
			}
		}
	}

}
