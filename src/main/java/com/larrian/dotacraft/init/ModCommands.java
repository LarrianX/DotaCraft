package com.larrian.dotacraft.init;

import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.command.*;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;

public class ModCommands {
    private static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        BecomeHeroCommand.register(dispatcher);
        UndoCommand.register(dispatcher);
        SetAttributesCommand.register(dispatcher);
        SyncCommand.register(dispatcher);
        UpCommand.register(dispatcher);
        ResetCommand.register(dispatcher);
    }

    public static void registerModCommands() {
        DotaCraft.LOGGER.info("Registering Mod commands for " + DotaCraft.MOD_ID);
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> registerCommands(dispatcher)));
    }
}