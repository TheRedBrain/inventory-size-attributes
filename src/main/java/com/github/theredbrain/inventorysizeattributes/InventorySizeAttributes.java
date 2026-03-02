package com.github.theredbrain.inventorysizeattributes;

import com.github.theredbrain.inventorysizeattributes.config.ServerConfig;
import com.github.theredbrain.inventorysizeattributes.entity.player.DuckPlayerEntityMixin;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InventorySizeAttributes implements ModInitializer {
	public static final String MOD_ID = "inventorysizeattributes";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ServerConfig SERVER_CONFIG;

	public static Holder<Attribute> HOTBAR_SLOT_AMOUNT;
	public static Holder<Attribute> INVENTORY_SLOT_AMOUNT;

	public static int getActiveHotbarSlotAmount(Player playerEntity) {
		return ((DuckPlayerEntityMixin) playerEntity).inventorysizeattributes$getActiveHotbarSlotAmount();
	}

	public static int getActiveInventorySlotAmount(Player playerEntity) {
		return ((DuckPlayerEntityMixin) playerEntity).inventorysizeattributes$getActiveInventorySlotAmount();
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Inventories come in different sizes now!");
		SERVER_CONFIG = ConfigApiJava.registerAndLoadConfig(ServerConfig::new, RegisterType.BOTH);
	}

	public static Identifier identifier(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

}