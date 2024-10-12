package com.dota2.command;

import com.dota2.component.hero.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.dota2.component.hero.SyncedHeroComponent.HEALTH;
import static com.dota2.component.ModComponents.*;

public class BecomeHero {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
            CommandManager.literal("become_hero")
                .then(CommandManager.argument("max health",     DoubleArgumentType.doubleArg(SyncedMaxValuesComponent.MIN, SyncedMaxValuesComponent.MAX))
                    .then(CommandManager.argument("max mana",   DoubleArgumentType.doubleArg(SyncedMaxValuesComponent.MIN, SyncedMaxValuesComponent.MAX))
                        .then(CommandManager.argument("team",   IntegerArgumentType.integer(0, 1))
                        .executes(BecomeHero::execute))))
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player != null) {
            EntityAttributeInstance attribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            HeroComponent heroComponent = player.getComponent(HERO_COMPONENT);

            if (attribute != null && !heroComponent.isHero()) {
                double maxHealth = DoubleArgumentType.getDouble(context, "max health");
                double maxMana = DoubleArgumentType.getDouble(context, "max mana");
                int teamIndex = IntegerArgumentType.getInteger(context, "team");

                // Выставление старых значений
                OldValuesComponent oldValuesComponent = player.getComponent(OLD_VALUES_COMPONENT);
                oldValuesComponent.setOldHealth((int) player.getHealth());
                oldValuesComponent.setOldMaxHealth((int) attribute.getBaseValue());
                // Выставление макс. хп
                attribute.setBaseValue(HEALTH);
                player.setHealth(HEALTH);
                // Выставление максимальных значений
                MaxValuesComponent maxValuesComponent = player.getComponent(MAX_VALUES_COMPONENT);
                maxValuesComponent.setMaxHealth(maxHealth);
                maxValuesComponent.setMaxMana(maxMana);
                maxValuesComponent.sync();
                // Выставление значений хп и маны
                ValuesComponent valuesComponent = player.getComponent(VALUES_COMPONENT);
                valuesComponent.setHealth(maxHealth);
                valuesComponent.setMana(maxMana);
                valuesComponent.sync();
                // Выставление героем
                heroComponent.setHero(true);
                heroComponent.sync();
                // Переход в команду
                MinecraftServer server = context.getSource().getServer();
                Scoreboard scoreboard = server.getScoreboard();
                String teamName = teamIndex == 0 ? "light" : "black";
                Team team = scoreboard.getTeam(teamName);
                if (team != null) {
                    scoreboard.addPlayerToTeam(player.getEntityName(), team);
                }
            }
        }
        return 1;
    }
}
