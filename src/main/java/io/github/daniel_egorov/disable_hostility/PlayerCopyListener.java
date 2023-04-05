package io.github.daniel_egorov.disable_hostility;


import org.quiltmc.qsl.entity.event.api.ServerPlayerEntityCopyCallback;

import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerCopyListener implements ServerPlayerEntityCopyCallback {
  @Override
  public void onPlayerCopy(ServerPlayerEntity copy, ServerPlayerEntity original, boolean wasDeath) {
    ((SaveHostility) copy).setHostility(((SaveHostility) original).getHostility());
  }
}
