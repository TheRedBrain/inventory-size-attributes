package com.github.theredbrain.inventorysizeattributes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class InventorySizeAttributesClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {

		// Packets
		ClientPlayNetworking.registerGlobalReceiver(InventorySizeAttributes.ServerConfigSyncPacket.PACKET_ID, (payload, context) -> {
			InventorySizeAttributes.serverConfig = payload.serverConfig();
		});
	}
}