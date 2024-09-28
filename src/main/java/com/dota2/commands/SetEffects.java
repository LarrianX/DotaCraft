package com.dota2.commands;

import com.dota2.components.EffectComponent;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.dota2.components.ModComponents.EFFECT_COMPONENT;


public class SetEffects {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("set_effect")
                        .then(CommandManager.argument("name", StringArgumentType.string())
                                .then(CommandManager.argument("value", DoubleArgumentType.doubleArg())
                                        .executes(SetEffects::execute)))
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            EffectComponent component = player.getComponent(EFFECT_COMPONENT);
            String name = StringArgumentType.getString(context, "name");
            double value = DoubleArgumentType.getDouble(context, "value");
            component.getAmplifiers().put(name, value);
        }

        return 1;
    }
}
