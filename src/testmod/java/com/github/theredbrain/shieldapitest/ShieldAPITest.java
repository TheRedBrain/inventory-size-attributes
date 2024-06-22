package com.github.theredbrain.shieldapitest;

import com.github.theredbrain.shieldapi.ShieldAPI;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class ShieldAPITest implements ModInitializer {
    public static final String MOD_ID = "shieldapitest";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Shield API Test initialized!");
        ShieldAPI.registerShieldWithVanillaModel(
                Identifier.of(MOD_ID, "test_vanilla_shield"),
                null,
                () -> Ingredient.ofItems(Items.IRON_INGOT),
                new ArrayList<>(),
                new FabricItemSettings().maxDamage(150),
                ItemGroups.COMBAT
        );
        ShieldAPI.registerNewShieldWithCustomModel(
                Identifier.of(MOD_ID, "test_buckler"),
                null,
                () -> Ingredient.ofItems(Items.IRON_INGOT),
                new ArrayList<>(),
                new FabricItemSettings().maxDamage(150),
                ItemGroups.COMBAT
        );
        List<Pair<EntityAttribute, EntityAttributeModifier>> test_buckler_modifiers = new ArrayList<>();
        test_buckler_modifiers.add(
                new Pair<>(
                        EntityAttributes.GENERIC_ARMOR,
                        new EntityAttributeModifier(
                                "test_buckler_armor",
                                2.0,
                                EntityAttributeModifier.Operation.ADDITION
                        )
                )
        );
        ShieldAPI.addNewCustomShieldItem(
                Identifier.of(MOD_ID, "test_buckler"),
                null,
                () -> Ingredient.ofItems(Items.IRON_INGOT),
                test_buckler_modifiers,
                new FabricItemSettings().maxDamage(150),
                ItemGroups.COMBAT
        );
    }
}