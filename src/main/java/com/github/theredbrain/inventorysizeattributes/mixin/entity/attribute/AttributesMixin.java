package com.github.theredbrain.inventorysizeattributes.mixin.entity.attribute;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributes;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Attributes.class)
public class AttributesMixin {
	static {
		InventorySizeAttributes.HOTBAR_SLOT_AMOUNT = Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, InventorySizeAttributes.identifier("hotbar_slot_amount"), new RangedAttribute("attribute.name.hotbar_slot_amount", 0.0, 0.0, 9.0).setSyncable(true));
		InventorySizeAttributes.INVENTORY_SLOT_AMOUNT = Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, InventorySizeAttributes.identifier("inventory_slot_amount"), new RangedAttribute("attribute.name.inventory_slot_amount", 0.0, 0.0, 27.0).setSyncable(true));
	}
}
