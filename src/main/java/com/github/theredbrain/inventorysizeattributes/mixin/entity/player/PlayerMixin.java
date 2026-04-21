package com.github.theredbrain.inventorysizeattributes.mixin.entity.player;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributes;
import com.github.theredbrain.inventorysizeattributes.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.inventorysizeattributes.screen.DuckScreenHandlerMixin;
import com.google.common.collect.HashMultimap;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements DuckPlayerEntityMixin {

	@Shadow
	public abstract Inventory getInventory();

	@Shadow
	public AbstractContainerMenu containerMenu;

	protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level world) {
		super(entityType, world);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	public void inventorysizeattributes$tick(CallbackInfo ci) {
		if (!this.level().isClientSide()) {
			this.getAttributes().addTransientAttributeModifiers(getNaturalAttributeModifiers(this.level()));
		}
		if (this.level().getGameTime() % 20L == 10) {
			this.inventorysizeattributes$updateActiveInventorySlots();
			if (!this.level().isClientSide()) {
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

		Player playerEntity = ((Player) (Object) this);
		if (this.containerMenu instanceof DuckScreenHandlerMixin screenHandler) {
			screenHandler.inventorysizeattributes$updateActiveHotbarSlots(playerEntity);
			screenHandler.inventorysizeattributes$updateActiveInventorySlots(playerEntity);
		}
	}

	@Unique
	private void inventorysizeattributes$ejectItemsFromInactiveInventorySlots() {
		Player playerEntity = ((Player) (Object) this);
		int hotbar_slot_amount = inventorysizeattributes$getActiveHotbarSlotAmount();
		int inventory_slot_amount = inventorysizeattributes$getActiveInventorySlotAmount();

		boolean bl = false;

		if (!this.level().isClientSide()) {
			for (int j = hotbar_slot_amount; j < 9; j++) {
				Inventory playerInventory = this.getInventory();

				if (!playerInventory.getItem(j).isEmpty()) {
					playerInventory.placeItemBackInInventory(playerInventory.removeItemNoUpdate(j));
					bl = true;
				}
			}
		}

		if (!this.level().isClientSide()) {
			for (int j = 9 + inventory_slot_amount; j < 36; j++) {
				Inventory playerInventory = this.getInventory();

				if (!playerInventory.getItem(j).isEmpty()) {
					playerInventory.placeItemBackInInventory(playerInventory.removeItemNoUpdate(j));
					bl = true;
				}
			}
		}

		if (bl && playerEntity instanceof ServerPlayer serverPlayerEntity) {
			serverPlayerEntity.sendSystemMessage(Component.translatable("hud.message.itemRemovedFromInactiveInventorySlots"), false);
		}
	}

	@Unique
	private HashMultimap<Holder<Attribute>, AttributeModifier> getNaturalAttributeModifiers(Level world) {
		HashMultimap<Holder<Attribute>, AttributeModifier> hashMultimap = HashMultimap.create();
		hashMultimap.put(InventorySizeAttributes.HOTBAR_SLOT_AMOUNT, new AttributeModifier(InventorySizeAttributes.identifier("natural_hotbar_slot_amount_modifier"), InventorySizeAttributes.SERVER_CONFIG.natural_player_hotbar_size.get(), AttributeModifier.Operation.ADD_VALUE));
		hashMultimap.put(InventorySizeAttributes.INVENTORY_SLOT_AMOUNT, new AttributeModifier(InventorySizeAttributes.identifier("natural_inventory_slot_amount_modifier"), InventorySizeAttributes.SERVER_CONFIG.natural_player_inventory_size.get(), AttributeModifier.Operation.ADD_VALUE));
		return hashMultimap;
	}

}
