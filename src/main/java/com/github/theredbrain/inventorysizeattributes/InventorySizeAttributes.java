package com.github.theredbrain.inventorysizeattributes;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InventorySizeAttributes implements ModInitializer {
	public static final String MOD_ID = "inventorysizeattributes";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Inventories come in different sizes now!");
	}
}