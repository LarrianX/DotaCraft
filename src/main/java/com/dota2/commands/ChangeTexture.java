package com.dota2.commands;

import com.dota2.DotaCraftClient;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class ChangeTexture {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("change")
                        .then(CommandManager.argument("index", IntegerArgumentType.integer()).executes(context -> {
                                    final int index = IntegerArgumentType.getInteger(context, "index");
                                    DotaCraftClient.setIndex(index);
                                    return 1;
                                }
                        ))
        );
    }
}
