package io.github.daniel_egorov.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.daniel_egorov.disable_hostility.SaveHostility;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(TargetGoal.class)
public abstract class TargetGoalMixin extends TrackTargetGoal {

  @Shadow
  protected LivingEntity targetEntity;

  public TargetGoalMixin(MobEntity entity, boolean checkVisibility) {
    super(entity, checkVisibility);
  }

  @Inject(method = "start", at = @At("HEAD"), cancellable = true)
  void onStart(CallbackInfo ci) {
    if (targetEntity instanceof PlayerEntity) {
      if (!((SaveHostility) targetEntity).getHostility()) {
        ci.cancel();
      }
    }
  }
}
