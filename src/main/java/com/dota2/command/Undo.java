package com.dota2.command;

import com.dota2.component.hero.HeroComponent;
import com.dota2.component.hero.OldValuesComponent;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.dota2.component.ModComponents.HERO_COMPONENT;
import static com.dota2.component.ModComponents.OLD_VALUES_COMPONENT;

public class Undo {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("dotacraft:undo")
                        .executes(Undo::execute)
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            EntityAttributeInstance attribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

            if (attribute != null) {
                HeroComponent heroComponent = player.getComponent(HERO_COMPONENT);
                OldValuesComponent OldValuesComponent = player.getComponent(OLD_VALUES_COMPONENT);

                player.setHealth((float) OldValuesComponent.getOldHealth());
                attribute.setBaseValue(OldValuesComponent.getOldMaxHealth());

                heroComponent.setHero(false);
            }
        }

        return 1;
    }
}
