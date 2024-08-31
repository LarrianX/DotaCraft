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
                        .then(CommandManager.argument("max health", IntegerArgumentType.integer(1, 1024))
                                .executes(BecomeHero::execute))
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            HeroAttributes component = player.getComponent(HERO_ATTRIBUTES);
            int max_health = IntegerArgumentType.getInteger(context, "max health");
//            int max_mana = IntegerArgumentType.getInteger(context, "max mana");

            component.setHero(!component.isHero());
            component.setHealth(max_health);
            component.setMaxHealth(max_health);
//            component.setMana(max_mana);
//            component.setMaxMana(max_mana);
        }
        return 1;
    }
}
