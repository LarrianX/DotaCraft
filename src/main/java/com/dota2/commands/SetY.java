package com.dota2.commands;

import com.dota2.DotaCraftClient;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class SetY {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("sety")
                        .then(CommandManager.argument("value", IntegerArgumentType.integer()).executes(context -> {
                                    final int value = IntegerArgumentType.getInteger(context, "value");
                                    DotaCraftClient.setY(value);
                                    return 1;
                                }
                        ))
        );
    }
}
