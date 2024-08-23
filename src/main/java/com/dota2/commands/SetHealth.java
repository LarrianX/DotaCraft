package com.dota2.commands;

import com.dota2.DotaCraftClient;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class SetHealth {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("sethealth")
                        .then(CommandManager.argument("value", FloatArgumentType.floatArg(0.0F, 20.0F)).executes(context -> {
                                    final float value = FloatArgumentType.getFloat(context, "value");
                                    DotaCraftClient.setHealth(value);
                                    return 1;
                                }
                        ))
        );
    }
}
