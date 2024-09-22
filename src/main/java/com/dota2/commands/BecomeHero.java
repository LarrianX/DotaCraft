package com.dota2.commands;

import com.dota2.components.HeroAttributes;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.dota2.components.ModComponents.HERO_ATTRIBUTES;

public class BecomeHero {
    public static final int HEALTH = 100;

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("become_hero")
                        .executes(BecomeHero::execute)
                        .then(CommandManager.argument("max health", IntegerArgumentType.integer(1, 30000))
                                .then(CommandManager.argument("max mana", IntegerArgumentType.integer(0, 30000))
                                        .executes(BecomeHero::execute_with_attributes)))
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            HeroAttributes component = player.getComponent(HERO_ATTRIBUTES);
            EntityAttributeInstance attribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

            if (attribute != null) {
                component.setOldHealth((int) player.getHealth());
                component.setOldMaxHealth((int) attribute.getBaseValue());

                player.setHealth(HEALTH);
                attribute.setBaseValue(HEALTH);

                component.setHero(true);
            }
        }
        return 1;
    }

    private static int execute_with_attributes(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            HeroAttributes component = player.getComponent(HERO_ATTRIBUTES);
            EntityAttributeInstance attribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

            if (attribute != null) {
                int maxHealth = IntegerArgumentType.getInteger(context, "max health");
                int maxMana = IntegerArgumentType.getInteger(context, "max mana");

                component.setOldHealth((int) player.getHealth());
                component.setOldMaxHealth((int) attribute.getBaseValue());

                player.setHealth(HEALTH);
                attribute.setBaseValue(HEALTH);

                component.setHealth(maxHealth);
                component.setMaxHealth(maxHealth);
                component.setMana(maxMana);
                component.setMaxMana(maxMana);

                component.setHero(true);
            }
        }
        return 1;
    }
}
