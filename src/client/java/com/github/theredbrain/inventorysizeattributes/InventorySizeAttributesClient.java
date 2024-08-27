package com.github.theredbrain.inventorysizeattributes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class InventorySizeAttributesClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {

		// Packets
		ClientPlayNetworking.registerGlobalReceiver(InventorySizeAttributes.ServerConfigSync.ID, (client, handler, buf, responseSender) -> {
			InventorySizeAttributes.serverConfig = InventorySizeAttributes.ServerConfigSync.read(buf);
		});
	}
}