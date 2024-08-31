package com.github.theredbrain.inventorysizeattributes.mixin.screen;

import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ScreenHandler.class)
public abstract class ScreenHandlerMixin {

	@Shadow
	@Final
	public DefaultedList<Slot> slots;

	/**
	 * @author TheRedBrain
	 * @reason WIP
	 */
	@Overwrite
	public boolean insertItem(ItemStack stack, int startIndex, int endIndex, boolean fromLast) {
		boolean bl = false;
		int i = startIndex;
		if (fromLast) {
			i = endIndex - 1;
		}

		Slot slot;
		ItemStack itemStack;
		if (stack.isStackable()) {
			while (!stack.isEmpty()) {
				if (fromLast) {
					if (i < startIndex) {
						break;
					}
				} else if (i >= endIndex) {
					break;
				}

				slot = (Slot) this.slots.get(i);
				itemStack = slot.getStack();
				if (!itemStack.isEmpty() && ItemStack.areItemsAndComponentsEqual(stack, itemStack) && slot.isEnabled()) {
					int j = itemStack.getCount() + stack.getCount();
					if (j <= stack.getMaxCount()) {
						stack.setCount(0);
						itemStack.setCount(j);
						slot.markDirty();
						bl = true;
					} else if (itemStack.getCount() < stack.getMaxCount()) {
						stack.decrement(stack.getMaxCount() - itemStack.getCount());
						itemStack.setCount(stack.getMaxCount());
						slot.markDirty();
						bl = true;
					}
				}

				if (fromLast) {
					--i;
				} else {
					++i;
				}
			}
		}

		if (!stack.isEmpty()) {
			if (fromLast) {
				i = endIndex - 1;
			} else {
				i = startIndex;
			}

			while (true) {
				if (fromLast) {
					if (i < startIndex) {
						break;
					}
				} else if (i >= endIndex) {
					break;
				}

				slot = (Slot) this.slots.get(i);
				itemStack = slot.getStack();
				if (itemStack.isEmpty() && slot.canInsert(stack) && slot.isEnabled()) {
					if (stack.getCount() > slot.getMaxItemCount()) {
						slot.setStack(stack.split(slot.getMaxItemCount()));
					} else {
						slot.setStack(stack.split(stack.getCount()));
					}

					slot.markDirty();
					bl = true;
					break;
				}

				if (fromLast) {
					--i;
				} else {
					++i;
				}
			}
		}

		return bl;
	}
}
