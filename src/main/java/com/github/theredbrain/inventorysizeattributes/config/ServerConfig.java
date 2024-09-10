package com.github.theredbrain.inventorysizeattributes.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(
		name = "server"
)
public class ServerConfig implements ConfigData {
	@Comment("""
			The default amount of hotbar slots.
			Must be between 0 and 9 (both inclusive)
			""")
	public int default_hotbar_slot_amount = 9;
	@Comment("""
			The default amount of inventory slots.
			Must be between 0 and 27 (both inclusive)
			""")
	public int default_inventory_slot_amount = 27;

	public ServerConfig() {
	}
}
