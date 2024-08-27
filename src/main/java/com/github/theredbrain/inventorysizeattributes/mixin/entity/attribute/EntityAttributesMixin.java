package com.github.theredbrain.inventorysizeattributes.mixin.entity.attribute;

import com.github.theredbrain.inventorysizeattributes.InventorySizeAttributes;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityAttributes.class)
public class EntityAttributesMixin {
    @Shadow
    private static EntityAttribute register(String id, EntityAttribute attribute) {
        throw new AssertionError();
    }

    static {
        InventorySizeAttributes.HOTBAR_SLOT_AMOUNT = register(InventorySizeAttributes.MOD_ID + ":generic.hotbar_slot_amount", new ClampedEntityAttribute("attribute.name.generic.hotbar_slot_amount", 0.0, 0.0, 9).setTracked(true));
        InventorySizeAttributes.INVENTORY_SLOT_AMOUNT = register(InventorySizeAttributes.MOD_ID + ":generic.inventory_slot_amount", new ClampedEntityAttribute("attribute.name.generic.inventory_slot_amount", 0.0, 0.0, 27).setTracked(true));
    }
}
