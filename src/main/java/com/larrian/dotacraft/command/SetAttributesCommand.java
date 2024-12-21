package com.larrian.dotacraft.command;

import com.larrian.dotacraft.attributes.CritChanceAttribute;
import com.larrian.dotacraft.attributes.RegenerationHealthAttribute;
import com.larrian.dotacraft.attributes.RegenerationManaAttribute;
import com.larrian.dotacraft.component.hero.HealthComponent;
import com.larrian.dotacraft.component.hero.ManaComponent;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.larrian.dotacraft.init.ModAttributes.*;
import static com.larrian.dotacraft.init.ModComponents.*;

public class SetAttributesCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("set")
                        .executes(SetAttributesCommand::setMax)
                        .then(CommandManager.argument("health", DoubleArgumentType.doubleArg(RegenerationHealthAttribute.MIN, RegenerationHealthAttribute.MAX))
                                .executes(SetAttributesCommand::setHealth)
                                .then(CommandManager.argument("mana", DoubleArgumentType.doubleArg(RegenerationManaAttribute.MIN, RegenerationManaAttribute.MAX))
                                        .executes(SetAttributesCommand::setMana)
                                        .then(CommandManager.argument("regeneration health", DoubleArgumentType.doubleArg(RegenerationHealthAttribute.MIN, RegenerationHealthAttribute.MAX))
                                                .executes(SetAttributesCommand::setRegenerationHealth)
                                                .then(CommandManager.argument("regeneration mana", DoubleArgumentType.doubleArg(RegenerationManaAttribute.MIN, RegenerationManaAttribute.MAX))
                                                        .executes(SetAttributesCommand::setRegenerationMana)
                                                        .then(CommandManager.argument("crit chance", DoubleArgumentType.doubleArg(CritChanceAttribute.MIN, CritChanceAttribute.MAX))
                                                                .executes(SetAttributesCommand::setCritChance)
                                                        ))))));
    }

    private static int setMax(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            ManaComponent manaComponent = player.getComponent(MANA_COMPONENT);
            manaComponent.setMana(player.getAttributeValue(REGENERATION_MANA));
            HealthComponent healthComponent = player.getComponent(HEALTH_COMPONENT);
            healthComponent.setHealth(player.getAttributeValue(REGENERATION_HEALTH));
        }

        return 1;
    }

    private static int setHealth(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            HealthComponent component = player.getComponent(HEALTH_COMPONENT);
            double health = DoubleArgumentType.getDouble(context, "health");
            component.setHealth(health);
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
            manaComponent.setMana(mana);
            manaComponent.sync();
        }

        return 1;
    }

    private static int setRegenerationHealth(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            setMana(context);
            EntityAttributeInstance attribute = player.getAttributeInstance(REGENERATION_HEALTH);
            if (attribute != null) {
                double regenerationHealth = DoubleArgumentType.getDouble(context, "regeneration health");
                attribute.setBaseValue(regenerationHealth);
            }
        }

        return 1;
    }

    private static int setRegenerationMana(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            setRegenerationHealth(context);
            EntityAttributeInstance attribute = player.getAttributeInstance(REGENERATION_MANA);
            if (attribute != null) {
                double regenerationMana = DoubleArgumentType.getDouble(context, "regeneration mana");
                attribute.setBaseValue(regenerationMana);
            }
        }

        return 1;
    }

    private static int setCritChance(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            setRegenerationMana(context);
            EntityAttributeInstance attribute = player.getAttributeInstance(CRIT_CHANCE);
            if (attribute != null) {
                double critChance = DoubleArgumentType.getDouble(context, "crit chance");
                attribute.setBaseValue(critChance);
            }
        }

        return 1;
    }
}