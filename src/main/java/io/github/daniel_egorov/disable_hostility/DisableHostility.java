package io.github.daniel_egorov.disable_hostility;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;
import org.quiltmc.qsl.entity.event.api.ServerPlayerEntityCopyCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import static net.minecraft.server.command.CommandManager.*;

public class DisableHostility implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("Disable Hostility");

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("{} Initializing...", mod.metadata().name());

		// /hostility enable {player_name}
		CommandRegistrationCallback.EVENT.register(
			(dispatcher, registryAccess, environment) -> dispatcher.register(
				literal("hostility")
				.requires(source -> source.hasPermissionLevel(4)).then(
					literal("enable").then(
						argument("player", EntityArgumentType.player()).executes(ctx -> {
							Entity player = EntityArgumentType.getEntity(ctx, "player");
							if (!(player instanceof PlayerEntity)) return 0;

							((SaveHostility) player).setHostility(true);

							return 1;
						})
					)
				)
			)
		);

		// /hostility disable {player_name}
		CommandRegistrationCallback.EVENT.register(
			(dispatcher, registryAccess, environment) -> dispatcher.register(
				literal("hostility")
				.requires(source -> source.hasPermissionLevel(4)).then(
					literal("disable").then(
						argument("player", EntityArgumentType.player()).executes(ctx -> {
							Entity player = EntityArgumentType.getEntity(ctx, "player");
							if (!(player instanceof PlayerEntity)) return 0;

							((SaveHostility) player).setHostility(false);

							return 1;
						})
					)
				)
			)
		);

		ServerPlayerEntityCopyCallback.EVENT.register(new PlayerCopyListener());

		LOGGER.info("{} Initialized!", mod.metadata().name());
	}
}