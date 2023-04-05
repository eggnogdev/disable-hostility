package io.github.daniel_egorov.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;

import io.github.daniel_egorov.disable_hostility.SaveHostility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity implements SaveHostility {

  @Unique
  private boolean hostility = true;

  public ServerPlayerEntityMixin(World world, BlockPos pos, float f, GameProfile gameProfile) {
    super(world, pos, f, gameProfile);
  }

  @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
  private void onWriteNbt(NbtCompound nbt, CallbackInfo ci) {
    nbt.putBoolean("hostility", hostility);
  }

  @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
  private void onReadNbt(NbtCompound nbt, CallbackInfo ci) {
    if (nbt.contains("hostility")) {
      hostility = nbt.getBoolean("hostility");
    }
  }

  @Override
  public void setHostility(boolean hostility) {
    this.hostility = hostility;
  }

  @Override
  public boolean getHostility() {
    return hostility;
  }
}
