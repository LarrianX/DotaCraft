package com.dota2.command;

import com.dota2.component.HeroComponent.SyncedMaxValuesComponent;
import com.dota2.component.HeroComponent.ValuesComponent;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.dota2.component.ModComponents.VALUES_COMPONENT;

public class SetAttributes {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("set")
                        .then(CommandManager.argument("health", DoubleArgumentType.doubleArg(0, SyncedMaxValuesComponent.LIMIT))
                                .executes(SetAttributes::setHealth)
                                .then(CommandManager.argument("mana", DoubleArgumentType.doubleArg(0, SyncedMaxValuesComponent.LIMIT))
                                        .executes(SetAttributes::setHealthAndMana)))
        );
    }

    private static int setHealth(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            ValuesComponent component = player.getComponent(VALUES_COMPONENT);
            double health = DoubleArgumentType.getDouble(context, "health");
            component.setHealth(health);
            component.sync();
        }

        return 1;
    }

    private static int setHealthAndMana(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            ValuesComponent component = player.getComponent(VALUES_COMPONENT);
            double health = DoubleArgumentType.getDouble(context, "health");
            double mana = DoubleArgumentType.getDouble(context, "mana");
            component.setHealth(health);
            component.setMana(mana);
            component.sync();
        }

        return 1;
    }
}
