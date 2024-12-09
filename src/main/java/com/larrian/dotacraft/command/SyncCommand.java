package com.larrian.dotacraft.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.larrian.dotacraft.init.ModComponents.*;

public class SyncCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("sync")
                        .executes(SyncCommand::execute)
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        EFFECT_COMPONENT.sync(player);
        HERO_COMPONENT.sync(player);
        VALUES_COMPONENT.sync(player);

        return 1;
    }
}
