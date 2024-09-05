package com.dota2.commands;

import com.dota2.components.HeroAttributes;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.dota2.components.ModComponents.HERO_ATTRIBUTES;

public class BecomeHero {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("become_hero")
                        .executes(BecomeHero::execute)
                        .then(CommandManager.argument("max health", IntegerArgumentType.integer(1, 1024))
                                .then(CommandManager.argument("max mana", IntegerArgumentType.integer(0, 1024))
                                        .executes(BecomeHero::execute_with_attributes)))
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            HeroAttributes component = player.getComponent(HERO_ATTRIBUTES);

            component.setHero(true);
        }
        return 1;
    }

    private static int execute_with_attributes(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            HeroAttributes component = player.getComponent(HERO_ATTRIBUTES);
            int maxHealth = IntegerArgumentType.getInteger(context, "max health");
            int maxMana = IntegerArgumentType.getInteger(context, "max mana");

            component.setHero(true);
            component.setHealth(maxHealth);
            component.setMaxHealth(maxHealth);
            component.setMana(maxMana);
            component.setMaxMana(maxMana);
        }
        return 1;
    }
}
