package com.dota2.command;

import com.dota2.component.EffectComponent;
import com.dota2.component.hero.HeroComponent;
import com.dota2.component.hero.OldValuesComponent;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.dota2.component.ModComponents.*;

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
                // Выставление старых значений
                OldValuesComponent OldValuesComponent = player.getComponent(OLD_VALUES_COMPONENT);
                player.setHealth((float) OldValuesComponent.getOldHealth());
                attribute.setBaseValue(OldValuesComponent.getOldMaxHealth());
                // Удаление amplifier с именем команды
                AbstractTeam team = player.getScoreboardTeam();
                if (team != null) {
                    EffectComponent effectComponent = player.getComponent(EFFECT_COMPONENT);
                    effectComponent.getAmplifiers().remove(team.getName(), 1.0);
                    effectComponent.sync();
                }
                // Удаление у игрока поле героя
                HeroComponent heroComponent = player.getComponent(HERO_COMPONENT);
                heroComponent.setHero(false);
                heroComponent.sync();
            }
        }

        return 1;
    }
}
