package com.github.theredbrain.inventorysizeattributes.mixin.entity.player;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributes;
import com.github.theredbrain.inventorysizeattributes.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.slotcustomizationapi.api.SlotCustomization;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements DuckPlayerEntityMixin {

	@Shadow public abstract PlayerInventory getInventory();

	@Shadow @Final public PlayerScreenHandler playerScreenHandler;
	@Unique
	private static final TrackedData<Integer> OLD_HOTBAR_SLOT_AMOUNT = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);

	@Unique
	private static final TrackedData<Integer> OLD_INVENTORY_SLOT_AMOUNT = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);

	// double-checking seems to be necessary because the attribute seems to be set after the check sometimes
	// could also be a sync issue
	@Unique
	private int shouldCheckForItemsInInactiveHotbarSlots = 5;

	@Unique
	private int shouldCheckForItemsInInactiveInventorySlots = 5;

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "initDataTracker", at = @At("RETURN"))
	protected void inventorysizeattributes$initDataTracker(DataTracker.Builder builder, CallbackInfo ci) {
		builder.add(OLD_HOTBAR_SLOT_AMOUNT, -1);
		builder.add(OLD_INVENTORY_SLOT_AMOUNT, -1);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	public void inventorysizeattributes$tick(CallbackInfo ci) {
		this.inventorysizeattributes$ejectItemsFromInactiveInventorySlots();
	}

	@Inject(method = "closeHandledScreen", at = @At("TAIL"))
	protected void inventorysizeattributes$closeHandledScreen(CallbackInfo ci) {
		this.shouldCheckForItemsInInactiveHotbarSlots = 2;
		this.shouldCheckForItemsInInactiveInventorySlots = 2;
	}

	@Inject(method = "onHandledScreenClosed", at = @At("TAIL"))
	protected void inventorysizeattributes$onHandledScreenClosed(CallbackInfo ci) {
		this.shouldCheckForItemsInInactiveHotbarSlots = 2;
		this.shouldCheckForItemsInInactiveInventorySlots = 2;
	}

	@Override
	public int inventorysizeattributes$getActiveHotbarSlotAmount() {
		return Math.min(9, Math.max(0, (Math.min(9, Math.max(0, InventorySizeAttributes.serverConfig.default_hotbar_slot_amount)) + this.inventorysizeattributes$getHotbarSlotAmount())));
	}

	@Override
	public int inventorysizeattributes$getHotbarSlotAmount() {
		return (int) this.getAttributeValue(InventorySizeAttributes.HOTBAR_SLOT_AMOUNT);
	}

	@Override
	public int inventorysizeattributes$getOldHotbarSlotAmount() {
		return this.dataTracker.get(OLD_HOTBAR_SLOT_AMOUNT);
	}

	@Override
	public void inventorysizeattributes$setOldHotbarSlotAmount(int hotbar_slot_amount) {
		this.dataTracker.set(OLD_HOTBAR_SLOT_AMOUNT, hotbar_slot_amount);
	}

	@Override
	public int inventorysizeattributes$getActiveInventorySlotAmount() {
		return Math.min(27, Math.max(0, (Math.min(27, Math.max(0, InventorySizeAttributes.serverConfig.default_inventory_slot_amount)) + this.inventorysizeattributes$getInventorySlotAmount())));
	}

	@Override
	public int inventorysizeattributes$getInventorySlotAmount() {
		return (int) this.getAttributeValue(InventorySizeAttributes.INVENTORY_SLOT_AMOUNT);
	}

	@Override
	public int inventorysizeattributes$getOldInventorySlotAmount() {
		return this.dataTracker.get(OLD_INVENTORY_SLOT_AMOUNT);
	}

	@Override
	public void inventorysizeattributes$setOldInventorySlotAmount(int inventory_slot_amount) {
		this.dataTracker.set(OLD_INVENTORY_SLOT_AMOUNT, inventory_slot_amount);
	}

	@Unique
	private void inventorysizeattributes$ejectItemsFromInactiveInventorySlots() {
		int hotbar_slot_amount = inventorysizeattributes$getActiveHotbarSlotAmount();
		if (this.inventorysizeattributes$getOldHotbarSlotAmount() != hotbar_slot_amount) {
			this.shouldCheckForItemsInInactiveHotbarSlots = 5;
			this.inventorysizeattributes$setOldHotbarSlotAmount(hotbar_slot_amount);
		}
		int inventory_slot_amount = inventorysizeattributes$getActiveInventorySlotAmount();
		if (this.inventorysizeattributes$getOldInventorySlotAmount() != inventory_slot_amount) {
			this.shouldCheckForItemsInInactiveInventorySlots = 5;
			this.inventorysizeattributes$setOldInventorySlotAmount(inventory_slot_amount);
		}

		boolean bl = false;

		// use a separate boolean to guarantee a check on login to account for changes to the server config
		if (this.shouldCheckForItemsInInactiveHotbarSlots > 0) {
			for (int i = 36; i < 45; i++) {
				((SlotCustomization) this.playerScreenHandler.slots.get(i)).slotcustomizationapi$setDisabledOverride(i >= 36 + hotbar_slot_amount);
			}
			if (!this.getWorld().isClient) {
				for (int j = hotbar_slot_amount; j < 9; j++) {
					PlayerInventory playerInventory = this.getInventory();

					if (!playerInventory.getStack(j).isEmpty()) {
						playerInventory.offerOrDrop(playerInventory.removeStack(j));
						bl = true;
					}
				}
			}
			this.shouldCheckForItemsInInactiveHotbarSlots--;
		}

		// use a separate boolean to guarantee a check on login to account for changes to the server config
		if (this.shouldCheckForItemsInInactiveInventorySlots > 0) {
			for (int i = 9; i < 36; i++) {
				((SlotCustomization) this.playerScreenHandler.slots.get(i)).slotcustomizationapi$setDisabledOverride(i >= 9 + inventory_slot_amount);
			}
			if (!this.getWorld().isClient) {
				for (int j = 9 + inventory_slot_amount; j < 36; j++) {
					PlayerInventory playerInventory = this.getInventory();

					if (!playerInventory.getStack(j).isEmpty()) {
						playerInventory.offerOrDrop(playerInventory.removeStack(j));
						bl = true;
					}
				}
			}
			this.shouldCheckForItemsInInactiveInventorySlots--;
		}

		if (bl && ((PlayerEntity) (Object) this) instanceof ServerPlayerEntity serverPlayerEntity) {
			serverPlayerEntity.sendMessage(Text.translatable("hud.message.itemRemovedFromInactiveInventorySlots"), false);
		}
	}
}
