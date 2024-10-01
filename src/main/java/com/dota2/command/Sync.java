package com.dota2.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.dota2.component.ModComponents.*;

public class Sync {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("sync")
                        .executes(Sync::execute)
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        EFFECT_COMPONENT.sync(player);
        HERO_COMPONENT.sync(player);
        VALUES_COMPONENT.sync(player);
        MAX_VALUES_COMPONENT.sync(player);

        return 1;
    }
}
