package com.github.theredbrain.inventorysizeattributes;

import com.github.theredbrain.inventorysizeattributes.config.ServerConfig;
import com.github.theredbrain.inventorysizeattributes.entity.player.DuckPlayerEntityMixin;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InventorySizeAttributes implements ModInitializer {
	public static final String MOD_ID = "inventorysizeattributes";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ServerConfig SERVER_CONFIG = ConfigApiJava.registerAndLoadConfig(ServerConfig::new);

	public static RegistryEntry<EntityAttribute> HOTBAR_SLOT_AMOUNT;
	public static RegistryEntry<EntityAttribute> INVENTORY_SLOT_AMOUNT;

	public static int getActiveHotbarSlotAmount(PlayerEntity playerEntity) {
		return Math.min(9, Math.max(0, (Math.min(9, Math.max(0, InventorySizeAttributes.SERVER_CONFIG.default_hotbar_slot_amount.get())) + ((DuckPlayerEntityMixin)playerEntity).inventorysizeattributes$getHotbarSlotAmount())));
	}

	public static int getActiveInventorySlotAmount(PlayerEntity playerEntity) {
		return Math.min(27, Math.max(0, (Math.min(27, Math.max(0, InventorySizeAttributes.SERVER_CONFIG.default_inventory_slot_amount.get())) + ((DuckPlayerEntityMixin)playerEntity).inventorysizeattributes$getInventorySlotAmount())));
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Inventories come in different sizes now!");
	}

	public static Identifier identifier(String path) {
		return Identifier.of(MOD_ID, path);
	}

}