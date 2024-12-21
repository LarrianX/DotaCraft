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
        if (player != null) {
            player.getComponent(HEALTH_COMPONENT).sync();
            player.getComponent(MANA_COMPONENT).sync();
            player.getComponent(HERO_COMPONENT).sync();
        }

        return 1;
    }
}
