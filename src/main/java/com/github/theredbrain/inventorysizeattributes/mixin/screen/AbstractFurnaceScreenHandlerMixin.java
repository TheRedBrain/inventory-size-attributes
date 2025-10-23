package com.github.theredbrain.inventorysizeattributes.mixin.screen;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributes;
import com.github.theredbrain.inventorysizeattributes.screen.DuckScreenHandlerMixin;
import com.github.theredbrain.slotcustomizationapi.api.SlotCustomization;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipePropertySet;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceScreenHandler.class)
public abstract class AbstractFurnaceScreenHandlerMixin extends AbstractRecipeScreenHandler implements DuckScreenHandlerMixin {
	public AbstractFurnaceScreenHandlerMixin(ScreenHandlerType<?> screenHandlerType, int i) {
		super(screenHandlerType, i);
	}

	@Inject(method = "Lnet/minecraft/screen/AbstractFurnaceScreenHandler;<init>(Lnet/minecraft/screen/ScreenHandlerType;Lnet/minecraft/recipe/RecipeType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/recipe/book/RecipeBookType;ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/Inventory;Lnet/minecraft/screen/PropertyDelegate;)V", at = @At("TAIL"))
	public void AbstractFurnaceScreenHandler(ScreenHandlerType<?> type, RecipeType<? extends AbstractCookingRecipe> recipeType, RegistryKey<RecipePropertySet> recipePropertySetKey, RecipeBookType category, int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate, CallbackInfo ci) {
		this.inventorysizeattributes$updateActiveHotbarSlots(playerInventory.player);
		this.inventorysizeattributes$updateActiveInventorySlots(playerInventory.player);
	}

	@Override
	public void inventorysizeattributes$updateActiveHotbarSlots(PlayerEntity playerEntity) {
		for (int i = 30; i < 39; i++) {
			((SlotCustomization) this.slots.get(i)).slotcustomizationapi$setDisabledOverride(i >= 30 + InventorySizeAttributes.getActiveHotbarSlotAmount(playerEntity));
		}
	}

	@Override
	public void inventorysizeattributes$updateActiveInventorySlots(PlayerEntity playerEntity) {
		for (int i = 3; i < 30; i++) {
			((SlotCustomization) this.slots.get(i)).slotcustomizationapi$setDisabledOverride(i >= 3 + InventorySizeAttributes.getActiveInventorySlotAmount(playerEntity));
		}
	}
}
