package com.dota2.commands;

import com.dota2.components.HeroAttributes;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.dota2.components.ModComponents.HERO_ATTRIBUTES;

public class Undo {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("undo")
                        .executes(Undo::execute)
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            HeroAttributes component = player.getComponent(HERO_ATTRIBUTES);
            EntityAttributeInstance attribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

            if (attribute != null) {
                player.setHealth(component.getOldHealth());
                attribute.setBaseValue(component.getOldMaxHealth());

                component.setHero(false);
            }
        }

        return 1;
    }
}
