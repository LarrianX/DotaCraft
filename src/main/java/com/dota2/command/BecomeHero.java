package com.dota2.command;

import com.dota2.component.hero.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.dota2.component.hero.SyncedHeroComponent.HEALTH;
import static com.dota2.component.ModComponents.*;

public class BecomeHero {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("become_hero")
                        .executes(BecomeHero::execute)
                        .then(CommandManager.argument("max health", DoubleArgumentType.doubleArg(SyncedMaxValuesComponent.MIN, SyncedMaxValuesComponent.MAX))
                                .then(CommandManager.argument("max mana", DoubleArgumentType.doubleArg(SyncedMaxValuesComponent.MIN, SyncedMaxValuesComponent.MAX))
                                        .executes(BecomeHero::executeWithAttributes)))
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            HeroComponent heroComponent = player.getComponent(HERO_COMPONENT);
            OldValuesComponent oldValuesComponent = player.getComponent(OLD_VALUES_COMPONENT);
            EntityAttributeInstance attribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

            if (attribute != null && !heroComponent.isHero()) {
                oldValuesComponent.setOldHealth((int) player.getHealth());
                oldValuesComponent.setOldMaxHealth((int) attribute.getBaseValue());

                player.setHealth(HEALTH);
                attribute.setBaseValue(HEALTH);

                heroComponent.setHero(true);
                heroComponent.sync();
            }
        }
        return 1;
    }

    private static int executeWithAttributes(CommandContext<ServerCommandSource> context) {
        execute(context);
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            ValuesComponent valuesComponent = player.getComponent(VALUES_COMPONENT);
            MaxValuesComponent maxValuesComponent = player.getComponent(MAX_VALUES_COMPONENT);

            double maxHealth = DoubleArgumentType.getDouble(context, "max health");
            double maxMana = DoubleArgumentType.getDouble(context, "max mana");

            maxValuesComponent.setMaxHealth(maxHealth);
            maxValuesComponent.setMaxMana(maxMana);
            maxValuesComponent.sync();
            valuesComponent.setHealth(maxHealth);
            valuesComponent.setMana(maxMana);
            valuesComponent.sync();
        }
        return 1;
    }
}
