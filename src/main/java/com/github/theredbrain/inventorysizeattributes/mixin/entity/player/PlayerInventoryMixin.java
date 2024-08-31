package com.github.theredbrain.inventorysizeattributes.mixin.entity.player;

import com.github.theredbrain.inventorysizeattributes.entity.player.DuckPlayerEntityMixin;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {

	@Shadow @Final public DefaultedList<ItemStack> main;

	@Shadow @Final public PlayerEntity player;

	@Shadow public int selectedSlot;

	@Shadow public abstract ItemStack getStack(int slot);

	@Shadow protected abstract boolean canStackAddMore(ItemStack existingStack, ItemStack stack);

	/**
	 * @author TheRedBrain
	 * @reason WIP
	 */
	@Overwrite
	public int getEmptySlot() {
		for(int i = 0; i < this.main.size(); ++i) {
			if (((ItemStack)this.main.get(i)).isEmpty() && this.inventorysizeattributes$isIndexInsideActiveInventorySize(i)) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * @author TheRedBrain
	 * @reason WIP
	 */
	@Overwrite
	public int getOccupiedSlotWithRoomForStack(ItemStack stack) {
		if (this.canStackAddMore(this.getStack(this.selectedSlot), stack)) {
			return this.selectedSlot;
		} else if (this.canStackAddMore(this.getStack(40), stack)) {
			return 40;
		} else {
			for(int i = 0; i < this.main.size(); ++i) {
				if (this.canStackAddMore((ItemStack)this.main.get(i), stack) && this.inventorysizeattributes$isIndexInsideActiveInventorySize(i)) {
					return i;
				}
			}

			return -1;
		}
	}

	@Unique
	private boolean inventorysizeattributes$isIndexInsideActiveInventorySize(int index) {
		return (index < 9 && index < ((DuckPlayerEntityMixin) this.player).inventorysizeattributes$getActiveHotbarSlotAmount()) || (index >= 9 && (index - 9) < ((DuckPlayerEntityMixin) this.player).inventorysizeattributes$getActiveInventorySlotAmount());
	}
}
