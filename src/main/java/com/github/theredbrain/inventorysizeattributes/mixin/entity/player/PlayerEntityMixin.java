package com.github.theredbrain.inventorysizeattributes.mixin.entity.player;
//
//import net.minecraft.entity.EntityType;
//import net.minecraft.entity.LivingEntity;
//import net.minecraft.entity.player.ItemCooldownManager;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.stat.Stat;
//import net.minecraft.world.World;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//
//@Mixin(PlayerEntity.class)
//public abstract class PlayerEntityMixin extends LivingEntity {
//
//	@Shadow
//	public abstract void incrementStat(Stat<?> stat);
//
//	@Shadow
//	public abstract ItemCooldownManager getItemCooldownManager();
//
//	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
//		super(entityType, world);
//	}
//
//}
