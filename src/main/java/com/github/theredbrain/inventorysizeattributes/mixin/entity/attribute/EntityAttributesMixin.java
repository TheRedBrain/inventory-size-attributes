package com.github.theredbrain.inventorysizeattributes.mixin.entity.attribute;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributes;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityAttributes.class)
public class EntityAttributesMixin {
	static {
		InventorySizeAttributes.HOTBAR_SLOT_AMOUNT = Registry.registerReference(Registries.ATTRIBUTE, InventorySizeAttributes.identifier("generic.hotbar_slot_amount"), new ClampedEntityAttribute("attribute.name.generic.hotbar_slot_amount", 0.0, 0.0, 9).setTracked(true));
		InventorySizeAttributes.INVENTORY_SLOT_AMOUNT = Registry.registerReference(Registries.ATTRIBUTE, InventorySizeAttributes.identifier("generic.inventory_slot_amount"), new ClampedEntityAttribute("attribute.name.generic.inventory_slot_amount", 0.0, 0.0, 27).setTracked(true));
	}
}
