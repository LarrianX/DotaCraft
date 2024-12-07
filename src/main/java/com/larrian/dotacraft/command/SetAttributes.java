package com.larrian.dotacraft.command;

import com.larrian.dotacraft.attributes.CritChance;
import com.larrian.dotacraft.component.hero.MaxValuesComponent;
import com.larrian.dotacraft.component.hero.SyncedMaxValuesComponent;
import com.larrian.dotacraft.component.hero.ValuesComponent;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.larrian.dotacraft.attributes.ModAttributes.CRIT_CHANCE;
import static com.larrian.dotacraft.component.ModComponents.MAX_VALUES_COMPONENT;
import static com.larrian.dotacraft.component.ModComponents.VALUES_COMPONENT;

public class SetAttributes {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("set")
                        .executes(SetAttributes::setMax)
                        .then(CommandManager.argument("health", DoubleArgumentType.doubleArg(SyncedMaxValuesComponent.MIN, SyncedMaxValuesComponent.MAX))
                                .executes(SetAttributes::setHealth)
                                .then(CommandManager.argument("mana", DoubleArgumentType.doubleArg(SyncedMaxValuesComponent.MIN, SyncedMaxValuesComponent.MAX))
                                        .executes(SetAttributes::setHealthAndMana)
                                        .then(CommandManager.argument("crit chance", DoubleArgumentType.doubleArg(CritChance.MIN, CritChance.MAX))
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
            EntityAttributeInstance attribute = player.getAttributeInstance(CRIT_CHANCE);
            if (attribute != null) {
                double critChance = DoubleArgumentType.getDouble(context, "crit chance");
                attribute.setBaseValue(critChance);
            }
        }

        return 1;
    }
}
