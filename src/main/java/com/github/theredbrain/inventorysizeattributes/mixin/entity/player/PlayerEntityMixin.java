package com.github.theredbrain.inventorysizeattributes.mixin.entity.player;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributes;
import com.github.theredbrain.inventorysizeattributes.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.inventorysizeattributes.screen.DuckScreenHandlerMixin;
import com.google.common.collect.HashMultimap;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements DuckPlayerEntityMixin {

	@Shadow
	public abstract PlayerInventory getInventory();

	@Shadow
	public ScreenHandler currentScreenHandler;

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	public void inventorysizeattributes$tick(CallbackInfo ci) {
		if (!this.getEntityWorld().isClient()) {
			this.getAttributes().addTemporaryModifiers(getNaturalAttributeModifiers(this.getEntityWorld()));
		}
		if (this.getEntityWorld().getTime() % 20L == 10) {
			this.inventorysizeattributes$updateActiveInventorySlots();
			if (!this.getEntityWorld().isClient()) {
				this.inventorysizeattributes$ejectItemsFromInactiveInventorySlots();
			}
		}
	}

	@Override
	public int inventorysizeattributes$getActiveHotbarSlotAmount() {
		return Math.min(9, Math.max(0, this.inventorysizeattributes$getHotbarSlotAmount()));
	}

	@Override
	public int inventorysizeattributes$getHotbarSlotAmount() {
		return (int) this.getAttributeValue(InventorySizeAttributes.HOTBAR_SLOT_AMOUNT);
	}

	@Override
	public int inventorysizeattributes$getActiveInventorySlotAmount() {
		return Math.min(27, Math.max(0, this.inventorysizeattributes$getInventorySlotAmount()));
	}

	@Override
	public int inventorysizeattributes$getInventorySlotAmount() {
		return (int) this.getAttributeValue(InventorySizeAttributes.INVENTORY_SLOT_AMOUNT);
	}

	@Unique
	private void inventorysizeattributes$updateActiveInventorySlots() {

		PlayerEntity playerEntity = ((PlayerEntity) (Object) this);
		if (this.currentScreenHandler instanceof DuckScreenHandlerMixin screenHandler) {
			screenHandler.inventorysizeattributes$updateActiveHotbarSlots(playerEntity);
			screenHandler.inventorysizeattributes$updateActiveInventorySlots(playerEntity);
		}
	}

	@Unique
	private void inventorysizeattributes$ejectItemsFromInactiveInventorySlots() {
		PlayerEntity playerEntity = ((PlayerEntity) (Object) this);
		int hotbar_slot_amount = inventorysizeattributes$getActiveHotbarSlotAmount();
		int inventory_slot_amount = inventorysizeattributes$getActiveInventorySlotAmount();

		boolean bl = false;

		if (!this.getEntityWorld().isClient()) {
			for (int j = hotbar_slot_amount; j < 9; j++) {
				PlayerInventory playerInventory = this.getInventory();

				if (!playerInventory.getStack(j).isEmpty()) {
					playerInventory.offerOrDrop(playerInventory.removeStack(j));
					bl = true;
				}
			}
		}

		if (!this.getEntityWorld().isClient()) {
			for (int j = 9 + inventory_slot_amount; j < 36; j++) {
				PlayerInventory playerInventory = this.getInventory();

				if (!playerInventory.getStack(j).isEmpty()) {
					playerInventory.offerOrDrop(playerInventory.removeStack(j));
					bl = true;
				}
			}
		}

		if (bl && playerEntity instanceof ServerPlayerEntity serverPlayerEntity) {
			serverPlayerEntity.sendMessage(Text.translatable("hud.message.itemRemovedFromInactiveInventorySlots"), false);
		}
	}

	@Unique
	private HashMultimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> getNaturalAttributeModifiers(World world) {
		HashMultimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> hashMultimap = HashMultimap.create();
		hashMultimap.put(InventorySizeAttributes.HOTBAR_SLOT_AMOUNT, new EntityAttributeModifier(InventorySizeAttributes.identifier("natural_hotbar_slot_amount_modifier"), InventorySizeAttributes.SERVER_CONFIG.natural_player_hotbar_size.get(), EntityAttributeModifier.Operation.ADD_VALUE));
		hashMultimap.put(InventorySizeAttributes.INVENTORY_SLOT_AMOUNT, new EntityAttributeModifier(InventorySizeAttributes.identifier("natural_inventory_slot_amount_modifier"), InventorySizeAttributes.SERVER_CONFIG.natural_player_inventory_size.get(), EntityAttributeModifier.Operation.ADD_VALUE));
		return hashMultimap;
	}

}
