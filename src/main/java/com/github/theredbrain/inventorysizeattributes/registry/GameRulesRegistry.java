package com.github.theredbrain.inventorysizeattributes.registry;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
import net.minecraft.world.GameRules;

public class GameRulesRegistry {
	public static final GameRules.Key<GameRules.IntRule> NATURAL_HOTBAR_SIZE =
			GameRuleRegistry.register("naturalHotbarSize", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(9, 0, 9));

	public static final GameRules.Key<GameRules.IntRule> NATURAL_INVENTORY_SIZE =
			GameRuleRegistry.register("naturalInventorySize", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(27, 0, 27));

	public static void init() {
	}
}
