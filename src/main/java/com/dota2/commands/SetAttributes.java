package com.dota2.commands;

import com.dota2.components.HeroComponents.SyncedMaxValuesComponent;
import com.dota2.components.HeroComponents.ValuesComponent;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.dota2.components.ModComponents.VALUES_COMPONENT;

public class SetAttributes {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("set")
                        .then(CommandManager.argument("health", IntegerArgumentType.integer(0, SyncedMaxValuesComponent.LIMIT))
                                .executes(SetAttributes::setHealth)
                                .then(CommandManager.argument("mana", IntegerArgumentType.integer(0, SyncedMaxValuesComponent.LIMIT))
                                        .executes(SetAttributes::setHealthAndMana)))
        );
    }

    private static int setHealth(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            ValuesComponent component = player.getComponent(VALUES_COMPONENT);
            int health = IntegerArgumentType.getInteger(context, "health");
            component.setHealth(health);
        }

        return 1;
    }

    private static int setHealthAndMana(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            ValuesComponent component = player.getComponent(VALUES_COMPONENT);
            int health = IntegerArgumentType.getInteger(context, "health");
            int mana = IntegerArgumentType.getInteger(context, "mana");
            component.setHealth(health);
            component.setMana(mana);
        }

        return 1;
    }
}
