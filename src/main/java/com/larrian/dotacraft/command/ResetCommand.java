package com.larrian.dotacraft.command;

import com.larrian.dotacraft.component.AttributesComponent;
import com.larrian.dotacraft.component.HealthComponent;
import com.larrian.dotacraft.component.HeroComponent;
import com.larrian.dotacraft.component.ManaComponent;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.larrian.dotacraft.init.ModAttributes.MAX_HEALTH;
import static com.larrian.dotacraft.init.ModAttributes.MAX_MANA;
import static com.larrian.dotacraft.init.ModComponents.*;

public class ResetCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("reset")
                        .executes(ResetCommand::execute)
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        if (player != null) {
            HeroComponent heroComponent = player.getComponent(HERO_COMPONENT);
            heroComponent.setHero(true);

            EntityAttributeInstance maxManaAttribute = player.getAttributeInstance(MAX_MANA);
            if (maxManaAttribute != null) {
                maxManaAttribute.setBaseValue(75);
            }
            ManaComponent manaComponent = player.getComponent(MANA_COMPONENT);
            manaComponent.set(0);
            manaComponent.sync();

            EntityAttributeInstance maxHealthAttribute = player.getAttributeInstance(MAX_HEALTH);
            if (maxHealthAttribute != null) {
                maxHealthAttribute.setBaseValue(120);
            }
            HealthComponent healthComponent = player.getComponent(HEALTH_COMPONENT);
            healthComponent.set(0);
            healthComponent.sync();

            AttributesComponent attributesComponent = player.getComponent(ATTRIBUTES_COMPONENT);
            attributesComponent.resetLevel();
            attributesComponent.setStrength(0);
            attributesComponent.setAgility(0);
            attributesComponent.setIntelligence(0);
            attributesComponent.sync();
        }

        return 1;
    }
}
