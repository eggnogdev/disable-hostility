package io.github.daniel_egorov.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.daniel_egorov.disable_hostility.SaveHostility;

import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity{

  protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
    super(entityType, world);
  }

  @Inject(method = "setTarget", at = @At("HEAD"), cancellable = true)
  private void onSetTarget(LivingEntity targetEntity, CallbackInfo ci) {
    if (targetEntity == null) return;

    if (targetEntity instanceof PlayerEntity) {
      if (!((SaveHostility) targetEntity).getHostility()) {
        ci.cancel();
      }
    }
  }
}
