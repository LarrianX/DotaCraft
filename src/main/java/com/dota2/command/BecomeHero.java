package com.dota2.command;

import com.dota2.component.HeroComponent.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.dota2.component.HeroComponent.SyncedHeroComponent.HEALTH;
import static com.dota2.component.ModComponents.*;

public class BecomeHero {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("become_hero")
                        .executes(BecomeHero::execute)
                        .then(CommandManager.argument("max health", IntegerArgumentType.integer(1, SyncedMaxValuesComponent.LIMIT))
                                .then(CommandManager.argument("max mana", IntegerArgumentType.integer(0, SyncedMaxValuesComponent.LIMIT))
                                        .executes(BecomeHero::execute_with_attributes)))
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

    private static int execute_with_attributes(CommandContext<ServerCommandSource> context) {
        execute(context);
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            ValuesComponent valuesComponent = player.getComponent(VALUES_COMPONENT);
            MaxValuesComponent maxValuesComponent = player.getComponent(MAX_VALUES_COMPONENT);

            int maxHealth = IntegerArgumentType.getInteger(context, "max health");
            int maxMana = IntegerArgumentType.getInteger(context, "max mana");

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
