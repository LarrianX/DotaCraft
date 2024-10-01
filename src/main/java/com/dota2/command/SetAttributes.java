package com.dota2.command;

import com.dota2.component.HeroComponent.MaxValuesComponent;
import com.dota2.component.HeroComponent.SyncedMaxValuesComponent;
import com.dota2.component.HeroComponent.SyncedValuesComponent;
import com.dota2.component.HeroComponent.ValuesComponent;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.dota2.component.ModComponents.MAX_VALUES_COMPONENT;
import static com.dota2.component.ModComponents.VALUES_COMPONENT;

public class SetAttributes {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("set")
                        .executes(SetAttributes::setMax)
                        .then(CommandManager.argument("health", DoubleArgumentType.doubleArg(0, SyncedMaxValuesComponent.LIMIT))
                                .executes(SetAttributes::setHealth)
                                .then(CommandManager.argument("mana", DoubleArgumentType.doubleArg(0, SyncedMaxValuesComponent.LIMIT))
                                        .executes(SetAttributes::setHealthAndMana)
                                        .then(CommandManager.argument("crit chance", DoubleArgumentType.doubleArg(0, SyncedValuesComponent.LIMIT_CRIT))
                                                .executes(SetAttributes::setAll)
                                        ))));
    }
    private static int setMax(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            ValuesComponent valuesComponent = player.getComponent(VALUES_COMPONENT);
            MaxValuesComponent maxValuesComponent = player.getComponent(MAX_VALUES_COMPONENT);
            valuesComponent.setMana(maxValuesComponent.getMaxMana());
            valuesComponent.setHealth(maxValuesComponent.getMaxHealth());
            valuesComponent.sync();
        }

        return 1;
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
            setHealth(context);
            ValuesComponent component = player.getComponent(VALUES_COMPONENT);
            double mana = DoubleArgumentType.getDouble(context, "mana");
            component.setMana(mana);
            component.sync();
        }

        return 1;
    }

    private static int setAll(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            setHealthAndMana(context);
            ValuesComponent component = player.getComponent(VALUES_COMPONENT);
            double critChance = DoubleArgumentType.getDouble(context, "crit chance");
            component.setCrit(critChance);
            component.sync();
        }

        return 1;
    }
}
