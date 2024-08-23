package com.dota2.commands;

import com.dota2.DotaCraft;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;

public class ModCommands {
    private static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        SetX.register(dispatcher);
        SetY.register(dispatcher);
        SetHealth.register(dispatcher);
    }

    public static void registerModCommands() {
        DotaCraft.LOGGER.info("Registering Mod commands for " + DotaCraft.MOD_ID);
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> registerCommands(dispatcher)));
    }
}