package com.github.theredbrain.inventorysizeattributes.config;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributes;
import me.fzzyhmstrs.fzzy_config.annotations.ConvertFrom;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;

@ConvertFrom(fileName = "server.json5", folder = "inventorysizeattributes")
public class ServerConfig extends Config {
	public ServerConfig() {
		super(InventorySizeAttributes.identifier("server"));
	}

	public ValidatedInt natural_player_hotbar_size = new ValidatedInt(9, 9, 0);
	public ValidatedInt natural_player_inventory_size = new ValidatedInt(27, 27, 0);
}
