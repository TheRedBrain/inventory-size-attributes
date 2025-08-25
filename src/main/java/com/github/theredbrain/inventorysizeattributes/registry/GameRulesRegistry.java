package com.github.theredbrain.inventorysizeattributes.registry;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
import net.minecraft.world.GameRules;

public class GameRulesRegistry {
	public static final GameRules.Key<DoubleRule> NATURAL_HOTBAR_SIZE =
			GameRuleRegistry.register("naturalHotbarSize", GameRules.Category.PLAYER, GameRuleFactory.createDoubleRule(9.0, 0.0, 9.0));

	public static final GameRules.Key<DoubleRule> NATURAL_INVENTORY_SIZE =
			GameRuleRegistry.register("naturalInventorySize", GameRules.Category.PLAYER, GameRuleFactory.createDoubleRule(27.0, 0.0, 27.0));

	public static void init() {
	}
}
