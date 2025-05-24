package com.larrian.dotacraft.command;

import com.larrian.dotacraft.command.custom.*;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;

public class ModCommands {
    private static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        BecomeHeroCommand.register(dispatcher);
        UndoCommand.register(dispatcher);
        SetAttributesCommand.register(dispatcher);
        SyncCommand.register(dispatcher);
        LevelCommand.register(dispatcher);
        ResetCommand.register(dispatcher);
    }

    public static void registerModCommands() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> registerCommands(dispatcher)));
    }
}