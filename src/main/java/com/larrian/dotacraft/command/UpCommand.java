package com.larrian.dotacraft.command;

import com.larrian.dotacraft.component.AttributesComponent;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.larrian.dotacraft.init.ModComponents.ATTRIBUTES_COMPONENT;

public class UpCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("level_up")
                        .executes(UpCommand::execute)
                        .then(CommandManager.argument("levels", IntegerArgumentType.integer(1, 30))
                                .executes(UpCommand::executeWithArg))
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        if (player != null) {
            AttributesComponent component = player.getComponent(ATTRIBUTES_COMPONENT);
            component.addLevel(1);
            component.sync();
        }
        return 1;
    }

    private static int executeWithArg(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        if (player != null) {
            int levels = IntegerArgumentType.getInteger(context, "levels");
            AttributesComponent component = player.getComponent(ATTRIBUTES_COMPONENT);
            component.addLevel(levels);
            component.sync();
        }
        return 1;
    }
}
