package com.github.theredbrain.inventorysizeattributes.config;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributes;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;

public class ClientConfig extends Config {

	public ClientConfig() {
		super(InventorySizeAttributes.identifier("client"));
	}

	public ValidatedBoolean show_inactive_inventory_slots = new ValidatedBoolean(false);
	public ValidatedBoolean show_inactive_hotbar_slots = new ValidatedBoolean(false);
	public ValidatedBoolean is_hotbar_centered = new ValidatedBoolean(true);

}
