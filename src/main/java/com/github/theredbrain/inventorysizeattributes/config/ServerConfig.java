package com.github.theredbrain.inventorysizeattributes.config;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributes;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;

public class ServerConfig extends Config {
	public ServerConfig() {
		super(InventorySizeAttributes.identifier("server"));
	}

	public ValidatedInt natural_player_hotbar_slot_amount = new ValidatedInt(9, 9, 0);
	public ValidatedInt natural_player_inventory_slot_amount = new ValidatedInt(27, 27, 0);
}