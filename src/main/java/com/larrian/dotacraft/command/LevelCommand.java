package com.larrian.dotacraft.command;

import com.larrian.dotacraft.component.HeroComponent;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.larrian.dotacraft.ModComponents.HERO_COMPONENT;

public class LevelCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("level")
                        .executes(LevelCommand::execute)
                        .then(CommandManager.argument("level", IntegerArgumentType.integer(0, 30))
                                .executes(LevelCommand::executeWithArg))
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        if (player != null) {
            HeroComponent component = player.getComponent(HERO_COMPONENT);
            component.addLevel(1);
            component.sync();
        }
        return 1;
    }

    private static int executeWithArg(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        if (player != null) {
            int levels = IntegerArgumentType.getInteger(context, "level");
            HeroComponent component = player.getComponent(HERO_COMPONENT);
            component.setLevel(levels);
            component.sync();
        }
        return 1;
    }
}
