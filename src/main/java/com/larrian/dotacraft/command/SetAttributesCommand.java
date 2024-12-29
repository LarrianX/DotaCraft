package com.larrian.dotacraft.command;

import com.larrian.dotacraft.component.hero.AttributesComponent;
import com.larrian.dotacraft.component.hero.HealthComponent;
import com.larrian.dotacraft.component.hero.ManaComponent;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.larrian.dotacraft.init.ModAttributes.REGENERATION_HEALTH;
import static com.larrian.dotacraft.init.ModAttributes.REGENERATION_MANA;
import static com.larrian.dotacraft.init.ModComponents.*;

public class SetAttributesCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("set")
                        .executes(SetAttributesCommand::setMax)
                        .then(CommandManager.argument("health", DoubleArgumentType.doubleArg(0, 30000))
                                .executes(SetAttributesCommand::setHealth)
                                .then(CommandManager.argument("mana", DoubleArgumentType.doubleArg(0, 30000))
                                        .executes(SetAttributesCommand::setMana)
                                        .then(CommandManager.argument("strength", DoubleArgumentType.doubleArg(0, 30000))
                                                .executes(SetAttributesCommand::setStrength)
                                                .then(CommandManager.argument("agility", DoubleArgumentType.doubleArg(0, 30000))
                                                        .executes(SetAttributesCommand::setAgility)
                                                        .then(CommandManager.argument("intelligence", DoubleArgumentType.doubleArg(0, 30000))
                                                                .executes(SetAttributesCommand::setIntelligence)
                                                        ))))));
    }

    private static int setMax(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            ManaComponent manaComponent = player.getComponent(MANA_COMPONENT);
            manaComponent.set(player.getAttributeValue(REGENERATION_MANA));
            HealthComponent healthComponent = player.getComponent(HEALTH_COMPONENT);
            healthComponent.set(player.getAttributeValue(REGENERATION_HEALTH));
//            AttributesComponent attributesComponent = player.getComponent(ATTRIBUTES_COMPONENT);
//            attributesComponent.setStrength(30000);
//            attributesComponent.setAgility(30000);
//            attributesComponent.setIntelligence(30000);
//            attributesComponent.sync();
        }

        return 1;
    }

    private static int setHealth(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            HealthComponent component = player.getComponent(HEALTH_COMPONENT);
            double health = DoubleArgumentType.getDouble(context, "health");
            component.set(health);
            component.sync();
        }

        return 1;
    }

    private static int setMana(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            setHealth(context);
            ManaComponent manaComponent = player.getComponent(MANA_COMPONENT);
            double mana = DoubleArgumentType.getDouble(context, "mana");
            manaComponent.set(mana);
            manaComponent.sync();
        }

        return 1;
    }

    private static int setStrength(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            setMana(context);
            AttributesComponent attributesComponent = player.getComponent(ATTRIBUTES_COMPONENT);
            double strength = DoubleArgumentType.getDouble(context, "strength");
            attributesComponent.setStrength(strength);
            attributesComponent.sync();
        }

        return 1;
    }

    private static int setAgility(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            setMana(context);
            AttributesComponent attributesComponent = player.getComponent(ATTRIBUTES_COMPONENT);
            double strength = DoubleArgumentType.getDouble(context, "strength");
            attributesComponent.setStrength(strength);
            double agility = DoubleArgumentType.getDouble(context, "agility");
            attributesComponent.setAgility(agility);
            attributesComponent.sync();
        }

        return 1;
    }

    private static int setIntelligence(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            setMana(context);
            AttributesComponent attributesComponent = player.getComponent(ATTRIBUTES_COMPONENT);
            double strength = DoubleArgumentType.getDouble(context, "strength");
            attributesComponent.setStrength(strength);
            double agility = DoubleArgumentType.getDouble(context, "agility");
            attributesComponent.setAgility(agility);
            double intelligence = DoubleArgumentType.getDouble(context, "intelligence");
            attributesComponent.setIntelligence(intelligence);
            attributesComponent.sync();
        }

        return 1;
    }
}
